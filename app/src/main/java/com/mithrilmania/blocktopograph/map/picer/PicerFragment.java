package com.mithrilmania.blocktopograph.map.picer;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.World;
import com.mithrilmania.blocktopograph.databinding.FragPicerBinding;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.util.ConvertUtil;
import com.mithrilmania.blocktopograph.util.UiUtil;
import com.mithrilmania.blocktopograph.util.math.DimensionVector3;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

public final class PicerFragment extends DialogFragment {

    private FragPicerBinding mBinding;
    private int stage;
    public static final int MAX_PIXELS = 64 * 1024 * 1024 / 16;
    public static final int MAX_PIXELS_32BIT = MAX_PIXELS / 2;

    World mWorld;
    Dimension mDimension;
    int mFindX, mFindZ;
    AsyncTask mOngoingTask;
    GenerateThread mOngoingThread;

    public static PicerFragment create(@NotNull World world, @NotNull DimensionVector3<Integer> location) {
        PicerFragment ret = new PicerFragment();
        ret.mWorld = world;
        ret.mDimension = location.dimension;
        ret.mFindX = location.x;
        ret.mFindZ = location.z;
        return ret;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setStyle();
//    }

    @NotNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_picer,
                container, false);
        Dialog dialog = getDialog();
        assert dialog != null;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle(R.string.picer_title);
        mBinding.finalButton.setOnClickListener(this::onClickFinalButton);
        View root = mBinding.getRoot();
        root.post(() -> mOngoingTask = new AnalyzeTask(this).execute());
        mBinding.scaleSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mBinding.setScale(getScaleFromBarValue(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        stage = 0;
        return root;
    }

    @Override
    public void onDismiss(@NotNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOngoingTask != null) mOngoingTask.cancel(true);
        if (mOngoingThread != null) mOngoingThread.cancel();
    }

    // Result callbacks from AnalyzeTask.

    @UiThread
    void showWarningForWrongChunks() {
        mBinding.warningWrongChunks.setVisibility(View.VISIBLE);
    }

    @UiThread
    void showWarningForOldChunks() {
        mBinding.warningOldChunks.setVisibility(View.VISIBLE);
    }

    @UiThread
    void showFailureDialogAndDismiss(@StringRes int resId) {
        Activity activity = getActivity();
        if (activity == null) return;
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setMessage(resId)
                .create();
        dialog.show();
        dismiss();
    }

    @UiThread
    void showFailureMsgAndDismiss(@StringRes int resId) {
        Activity activity = getActivity();
        if (activity == null) return;
        Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @UiThread
    private void onClickFinalButton(@NotNull View view) {
        switch (stage) {
            case 0:
                onClickGenerate();
                break;
            case 1:
                onClickSave(view);
                break;
        }
    }

    @Contract(pure = true)
    static private int getScaleFromBarValue(int barValue) {
        int scale = 1;
        for (int i1 = 1; i1 <= barValue; i1++) scale *= 2;
        return scale;
    }

    @UiThread
    void onAnalyzeDone(@NotNull Area area) {
        // Minimal 16x16 px per chunk.
        int size = area.calculateArea() * 256;

        // Oops.
        if (size > MAX_PIXELS) {
            showFailureDialogAndDismiss(R.string.picer_failed_too_large);
            return;
        }

        // How many levels could we scale.
        int levels = 1;
        for (int m = size; m <= MAX_PIXELS / 2; levels++) m *= 4;

        if (levels == 1) {
            // Cannot scale at all.
            mBinding.scaleBox.setVisibility(View.GONE);
            mBinding.scaleNot.setVisibility(View.VISIBLE);
            mBinding.scaleSeek.setMax(1);
            mBinding.scaleSeek.setProgress(1);
        } else {
            mBinding.scaleSeek.setMax(levels - 1);
            mBinding.scaleSeek.setProgress(levels - 1);
        }
        mBinding.selectCase.setVisibility(View.VISIBLE);
        mBinding.setArea(area);

        mBinding.finalButton.setVisibility(View.VISIBLE);
        mBinding.finalButton.setText(R.string.picer_btn_generate);
    }

    // Callback from .

    @UiThread
    private void onClickGenerate() {
        Activity activity = getActivity();
        if (activity == null) return;

        Area area = mBinding.getArea();
        int scale = getScaleFromBarValue(mBinding.scaleSeek.getProgress());
        Bitmap.Config config;
        if (area.calculateArea() * 256 * scale * scale > MAX_PIXELS_32BIT)
            config = Bitmap.Config.ARGB_4444;
        else config = Bitmap.Config.ARGB_8888;

        AlertDialog dialog = UiUtil.buildProgressWaitDialog(
                activity, R.string.picer_progress_generating, dialogInterface -> dismiss());
        dialog.show();

        mBinding.finalButton.setVisibility(View.GONE);
        mBinding.selectCase.setVisibility(View.GONE);

        mOngoingThread = new GenerateThread(this, area, scale, config, dialog);
        mOngoingThread.start();
    }

    @UiThread
    void onGenerationDone(@Nullable Bitmap bitmap, @Nullable AlertDialog dialog) {

        if (dialog != null) dialog.dismiss();

        if (bitmap == null) {
            Activity activity = getActivity();
            if (activity != null) Toast.makeText(
                    activity, R.string.general_failed, Toast.LENGTH_SHORT).show();
            dismiss();
            return;
        }

        stage = 1;
        mBinding.finalButton.setVisibility(View.VISIBLE);
        mBinding.finalButton.setText(R.string.picer_save);
        mBinding.finalButton.setTag(bitmap);
        mBinding.previewCase.setVisibility(View.VISIBLE);

        Glide.with(this).load(bitmap).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                // How could.
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                mBinding.scroll.post(() ->
                        mBinding.scroll.fullScroll(ScrollView.FOCUS_DOWN));
                return false;
            }
        }).into(mBinding.image);

    }

    @UiThread
    private void onClickSave(@NotNull View view) {
        Object o = view.getTag();
        if (!(o instanceof Bitmap)) return;
        Bitmap bmp = (Bitmap) o;
        view.setTag(null);
        String name = mWorld.getWorldDisplayName();
        name = ConvertUtil.getLegalFileName(name);
        new SaveTask(this, name).execute(bmp);
    }

    @UiThread
    void onSavedBitmap(@Nullable File file) {
        Activity activity = getActivity();
        if (activity == null) return;
        if (file == null)
            Toast.makeText(activity, R.string.general_failed, Toast.LENGTH_SHORT).show();
        else {
            MediaScannerConnection.scanFile(activity,
                    new String[]{file.getAbsolutePath()},
                    new String[]{"image/png"}, null);
            Snackbar snackbar = Snackbar.make(
                    activity.getWindow().getDecorView(),
                    getString(R.string.picer_saved), Snackbar.LENGTH_SHORT)
                    .setAction(R.string.general_share, v -> {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                        shareIntent.setType("image/jpeg");
                        v.getContext().startActivity(
                                Intent.createChooser(shareIntent,
                                        v.getContext().getString(R.string.picer_share_title)));
                    });
            snackbar.show();
            dismiss();
        }
    }

}
