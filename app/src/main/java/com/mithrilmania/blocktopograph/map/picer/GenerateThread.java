package com.mithrilmania.blocktopograph.map.picer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.map.renderer.MapRenderer;

import java.lang.ref.WeakReference;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

class GenerateThread extends Thread {

    private final Area area;
    private final WeakReference<PicerFragment> owner;
    private final int scale;
    private final Bitmap.Config config;
    private final WeakReference<AlertDialog> dialog;

    private boolean cancelled;

    GenerateThread(PicerFragment owner, Area area, int scale, Bitmap.Config config,
                   AlertDialog dialog) {
        this.owner = new WeakReference<>(owner);
        this.area = area;
        this.scale = scale;
        this.config = config;
        this.dialog = new WeakReference<>(dialog);
    }

    @Override
    public void run() {

        PicerFragment owner = this.owner.get();
        if (owner == null) return;

        Dimension dimension = owner.mDimension;
        MapRenderer renderer = dimension.defaultMapType.renderer;
        WorldData wdata = owner.mWorld.getWorldData();

        int chunksCountX = area.mMaxX - area.mMinX;
        int chunksCountY = area.mMaxZ - area.mMinZ;

        int size = scale * 16;

        Bitmap bitmap = Bitmap.createBitmap(chunksCountX * size,
                chunksCountY * size, config);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();

        int poolSize = 5;

        ExecutorService executor =
                new ThreadPoolExecutor(poolSize, poolSize, 0, TimeUnit.MILLISECONDS,
                        new ArrayBlockingQueue<>(poolSize << 1),
                        new ThreadPoolExecutor.CallerRunsPolicy());
        for (Area.Member member : area.getList()) {
            if (cancelled) {
                executor.shutdownNow();
                return;
            }
            executor.execute(() -> {
                Chunk chunk = wdata.getChunk(member.x, member.z, dimension);
                try {
                    renderer.renderToBitmap(chunk, canvas, dimension, member.x, member.z,
                            (member.x - area.mMinX) * size,
                            (member.z - area.mMinZ) * size,
                            scale, scale, paint, wdata);
                } catch (Exception e) {
                    Log.d(this, e);
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return;
        }

        owner = this.owner.get();
        if (owner == null) return;

        FragmentActivity activity = owner.getActivity();
        if (activity != null)
            activity.runOnUiThread(() -> this.owner.get().onGenerationDone(bitmap, dialog.get()));

        owner.mOngoingThread = null;
    }

    void cancel() {
        cancelled = true;
        interrupt();
    }
}