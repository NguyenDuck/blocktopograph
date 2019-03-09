package com.mithrilmania.blocktopograph.map.edit;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.map.Block;
import com.mithrilmania.blocktopograph.map.MapFragment;
import com.mithrilmania.blocktopograph.util.UiUtil;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AlertDialog;

public class SelectionBasedContextFreeEditTask extends
        AsyncTask<EditTarget, ChunkBasedEditResult, EditResultCode> {

    @NotNull
    private final EditFunction mFunction;

    @NotNull
    private final WeakReference<MapFragment> mOwner;
    private AlertDialog mWaitDialog;

    public SelectionBasedContextFreeEditTask(
            @NotNull EditFunction func, @NotNull MapFragment owner) {
        mFunction = func;
        mOwner = new WeakReference<>(owner);
    }

    @Override
    protected void onPreExecute() {
        MapFragment owner = mOwner.get();
        Activity activity;
        if (owner == null || (activity = owner.getActivity()) == null) return;
        mWaitDialog = UiUtil.buildProgressWaitDialog(
                activity, R.string.general_please_wait, this::onCancel
        );
        mWaitDialog.show();
    }

    @Override
    protected EditResultCode doInBackground(EditTarget... editTargets) {
        for (EditTarget editTarget : editTargets) {
            editTarget.forEachXyzd(this::randomEdit);
        }
        return null;
    }

    private int randomEdit(Chunk chunk, int x, int y, int z) {
        if ((chunk.getBlockRuntimeId(x, y, z) & 0xffff00) == (Block.B_50_0_TORCH.getRuntimeId() & 0xffff00))
            chunk.setBlockRuntimeId(x, y, z, 1, Block.B_20_0_GLASS.getRuntimeId());
        return 0;
    }

    private void onCancel(DialogInterface dialogInterface) {
        cancel(true);
        mWaitDialog = null;
    }

    @Override
    protected void onPostExecute(EditResultCode editResultCode) {
        if (mWaitDialog != null) {
            mWaitDialog.dismiss();
        }
    }
}
