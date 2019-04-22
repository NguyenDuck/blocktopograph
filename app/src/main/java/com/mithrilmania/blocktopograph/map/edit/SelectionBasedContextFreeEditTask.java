package com.mithrilmania.blocktopograph.map.edit;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.map.Biome;
import com.mithrilmania.blocktopograph.map.KnownBlock;
import com.mithrilmania.blocktopograph.map.MapFragment;
import com.mithrilmania.blocktopograph.util.UiUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import androidx.appcompat.app.AlertDialog;

public class SelectionBasedContextFreeEditTask extends
        AsyncTask<EditTarget, ChunkBasedEditResult, EditResultCode> {

    @NotNull
    private final EditFunction mFunction;

    @Nullable
    private Bundle mArgs;

    @NotNull
    private final WeakReference<MapFragment> mOwner;
    private AlertDialog mWaitDialog;

    public SelectionBasedContextFreeEditTask(
            @NotNull EditFunction func, @Nullable Bundle args, @NotNull MapFragment owner) {
        mFunction = func;
        mArgs = args;
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
        owner.world.setHaveBackgroundJob(true);
    }

    @Override
    protected EditResultCode doInBackground(EditTarget... editTargets) {

        switch (mFunction) {
            case LAMPSHADE: {
                SnrConfig cfg = new SnrConfig();
                cfg.searchMode = 2;
                cfg.placeMode = 1;
                cfg.searchBlockMain = KnownBlock.B_50_0_TORCH;
                cfg.placeBlockMain = KnownBlock.B_20_0_GLASS;
                cfg.ignoreSubId = true;
                return doSnr(cfg, editTargets);
            }
            case SNR: {
                Serializable ser;
                if (mArgs == null || !((ser = mArgs.getSerializable(SearchAndReplaceFragment.CONFIG)) instanceof SnrConfig))
                    return EditResultCode.GENERAL_FAILURE;
                return doSnr((SnrConfig) ser, editTargets);
            }
            case DCHUNK:
                return doDchunk(editTargets);
            case CHBIOME: {
                Serializable serFrom, serTo;
                if (mArgs == null || !((serTo = mArgs.getSerializable(ChBiomeFragment.KEY_TO)) instanceof Biome))
                    return EditResultCode.GENERAL_FAILURE;
                serFrom = mArgs.getSerializable(ChBiomeFragment.KEY_FROM);
                return doChBiome(serFrom instanceof Biome ? (Biome) serFrom : null, (Biome) serTo, editTargets);
            }
        }

        return null;
    }

    private EditResultCode doSnr(SnrConfig cfg, @NotNull EditTarget... editTargets) {
        SnrEdit edit = new SnrEdit(cfg);
        for (EditTarget editTarget : editTargets) {
            editTarget.setMaxError(Integer.MAX_VALUE);
            editTarget.forEachXyz(edit);
        }
        return EditResultCode.SUCCESS;
    }

    private EditResultCode doDchunk(@NotNull EditTarget... editTargets) {
        DchunkEdit edit = new DchunkEdit();
        for (EditTarget editTarget : editTargets) {
            editTarget.setMaxError(Integer.MAX_VALUE);
            editTarget.forEachChunk(edit);
        }
        return EditResultCode.SUCCESS;
    }

    private EditResultCode doChBiome(@Nullable Biome from, @NotNull Biome to,
                                     @NotNull EditTarget... editTargets) {
        ChBiomeEdit edit = new ChBiomeEdit(from, to);
        for (EditTarget editTarget : editTargets) {
            editTarget.setMaxError(Integer.MAX_VALUE);
            editTarget.forEachXz(edit);
        }
        return EditResultCode.SUCCESS;
    }

    private void onCancel(DialogInterface dialogInterface) {
        cancel(true);
        mWaitDialog = null;
        MapFragment owner;
        if ((owner = mOwner.get()) != null)
            owner.world.setHaveBackgroundJob(false);
    }

    @Override
    protected void onPostExecute(EditResultCode editResultCode) {
        if (mWaitDialog != null) {
            mWaitDialog.dismiss();
        }
        MapFragment owner = mOwner.get();
        Activity activity;
        if (owner != null && (activity = owner.getActivity()) != null) {
            owner.world.setHaveBackgroundJob(false);
            switch (editResultCode) {
                case SUCCESS:
                    Toast.makeText(activity, R.string.general_done, Toast.LENGTH_SHORT).show();
                    owner.refreshAfterEdit();
                    break;
                default:
                    Toast.makeText(activity, R.string.general_failed, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
