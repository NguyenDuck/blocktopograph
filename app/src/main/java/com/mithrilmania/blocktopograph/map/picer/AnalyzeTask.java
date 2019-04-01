package com.mithrilmania.blocktopograph.map.picer;

import android.app.Activity;
import android.graphics.Rect;
import android.os.AsyncTask;

import com.litl.leveldb.DB;
import com.litl.leveldb.Iterator;
import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.World;
import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.util.UiUtil;

import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

class AnalyzeTask extends AsyncTask<Void, Void, Rect> {

    private final WeakReference<PicerFragment> owner;
    private boolean hasWrongChunks;
    private boolean hasOldChunks;
    private AlertDialog waitDialog;

    AnalyzeTask(PicerFragment owner) {
        this.owner = new WeakReference<>(owner);
    }

    @Override
    protected void onPreExecute() {
        PicerFragment owner = this.owner.get();
        Activity activity;
        if (owner == null || (activity = owner.getActivity()) == null) {
            cancel(true);
            return;
        }
        waitDialog = UiUtil.buildProgressWaitDialog(activity, R.string.picer_progress_analyzing,
                dialogInterface -> {
                    PicerFragment diaFrag = AnalyzeTask.this.owner.get();
                    if (diaFrag != null) diaFrag.dismiss();
                });
        waitDialog.show();
    }

    @Override
    @Nullable
    protected Rect doInBackground(Void... voids) {

        PicerFragment owner = this.owner.get();
        if (owner == null) return null;

        // Get db.
        DB db = null;
        getDb:
        {
            World world = owner.mWorld;
            if (world == null) break getDb;
            WorldData worldData = world.getWorldData();
            try {
                worldData.openDB();
            } catch (Exception e) {
                break getDb;
            }
            db = worldData.db;
        }
        if (db == null) return null;

        Dimension dimension = owner.mDimension;
        int verKeyLenOfDim;
        switch (dimension) {
            case OVERWORLD:
                verKeyLenOfDim = 9;
                break;
            case NETHER:
            case END:
                verKeyLenOfDim = 13;
                break;
            default:
                return null;
        }

        // We used to separate world into areas or clusters, now that
        // we have selection so just measure whole world here.
        // If it's too large we just say select before using this.
        //List<Area> areas = new ArrayList<>(32);
        //Area.maxDist = 10;
        Rect rect = null;

        // Iterate over all items.
        try {
            db.put(new byte[]{0, 1, 2, 3, 0, 1, 2, 3, 118}, new byte[]{0});
            Iterator iterator = db.iterator();
            boolean cancelled = false;
            int loopCount = 0;
            loop:
            for (iterator.seekToFirst(); iterator.isValid(); iterator.next(), loopCount++) {

                if (isCancelled()) {
                    cancelled = true;
                    break;
                }

                // Is it a key for a chunk of current dim's version record?
                byte[] key = iterator.getKey();
                if (key.length != verKeyLenOfDim) continue;
                if (key[verKeyLenOfDim - 1] != (byte) 0x76) continue;
                ByteBuffer byteBuffer = ByteBuffer.wrap(key);
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                int x = byteBuffer.getInt();
                int z = byteBuffer.getInt();
                // Wrong dim.
                if (verKeyLenOfDim == 13 && dimension.id != byteBuffer.getInt()) continue;
                byte[] value = iterator.getValue();
                Version version = Version.getVersion(value);
                // Record unsupported stuff and skip.
                switch (version) {
                    case ERROR:
                    case NULL:
                        hasWrongChunks = true;
                        continue loop;
                    case OLD_LIMITED:
                        hasOldChunks = true;
                        continue loop;
                }

                if (rect != null) {
                    if (x < rect.left) rect.left = x;
                    else if (x > rect.right) rect.right = x;
                    if (z < rect.top) rect.top = z;
                    else if (z > rect.bottom) rect.bottom = z;
                    if (rect.right - rect.left > PicerFragment.MAX_LENGTH
                            || rect.bottom - rect.top > PicerFragment.MAX_LENGTH
                            || loopCount % 36 == 0 && (rect.right - rect.left) * (rect.bottom - rect.top) > PicerFragment.MAX_AREA)
                        break;
                } else {
                    rect = new Rect(x, z, x, z);
                }

                // Add the chunk to area list.
//                add:
//                {
//                    for (Area area : areas) {
//                        // Try add to existing areas.
//                        if (area.add(x, z)) break add;
//                    }
//                    // Failed then create new.
//                    areas.add(new Area(x, z));
//                }
//                // As more chunks were read maybe we can merge more of them.
//                if (loopCount > 16) Area.absMergeList(areas);
            }
            iterator.close();
            if (cancelled) return null;
        } catch (Throwable e) {
            Log.d(this, e);
            return null;
        }

        // Final merge. Redundant? Who cares.
//        Area.absMergeList(areas);
//
//        owner = this.owner.get();
//        if (owner == null) return null;
//
//        if (areas.size() == 0) return null;
//        if (areas.size() == 1) return areas.get(0);
//
//        // Then should decide which area to use based on camera position.
//        // For not let's ignore.
//        return areas.get(0);
        if (rect != null) {
            rect.left *= 16;
            rect.top *= 16;
            rect.right *= 16 + 15;
            rect.bottom *= 16 + 15;
        }
        return rect;
    }

    @Override
    protected void onCancelled() {
        PicerFragment owner = this.owner.get();
        if (owner != null) {
            owner.mOngoingTask = null;
            waitDialog.dismiss();
        }
    }

    @Override
    protected void onPostExecute(@Nullable Rect rect) {

        // We finished too late. No longer needed.
        PicerFragment owner = this.owner.get();
        if (owner == null) return;
        owner.mOngoingTask = null;
        waitDialog.dismiss();

        if (rect != null) {
            owner.onAnalyzeDone(rect);

        } else if (hasWrongChunks || hasOldChunks) {
            // Size 0 with exception, dismiss and show exception in dialog.
            @StringRes int strId;
            if (hasWrongChunks) strId = R.string.picer_failed_corrupt;
            else strId = R.string.picer_failed_old;
            owner.showFailureDialogAndDismiss(strId);

            // Otherwise a toast is enough.
        } else owner.showFailureMsgAndDismiss(R.string.picer_failed_nodata);

    }
}