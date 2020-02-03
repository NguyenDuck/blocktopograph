package com.mithrilmania.blocktopograph.map.picer;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

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
import com.mithrilmania.blocktopograph.map.OpenLongPressMenuHandler;
import com.mithrilmania.blocktopograph.util.ConvertUtil;
import com.mithrilmania.blocktopograph.util.UiUtil;

import java.io.File;

public final class PicerFragment extends DialogFragment {

    public static final int MAX_LENGTH = 2048;
    public static final int MAX_AREA = 64 * 64 * 256;
    public static final int MAX_SCALE = 32;
    private FragPicerBinding mBinding;
    Rect mRange;

    World mWorld;
    Dimension mDimension;
    AsyncTask mOngoingTask;
    private int stage = 0;
    GenerateThread mOngoingThread;
    private OpenLongPressMenuHandler mOpenLongPressMenuHandler;

    public static PicerFragment create(@NonNull World world, @NonNull Dimension dimension,
                                       @Nullable Rect range, @Nullable OpenLongPressMenuHandler openLongPressMenuHandler) {
        PicerFragment ret = new PicerFragment();
        ret.mWorld = world;
        ret.mDimension = dimension;
        ret.mRange = range;
        ret.mOpenLongPressMenuHandler = openLongPressMenuHandler;
        return ret;
    }

    private static boolean rangeCheck(@NonNull Rect rect) {
        int width = rect.right - rect.left;
        int height = rect.bottom - rect.top;
        return width <= MAX_LENGTH && height <= MAX_LENGTH && width * height <= MAX_AREA;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_picer,
                container, false);
        mBinding.finalButton.setOnClickListener(this::onClickFinalButton);
        mBinding.scaleSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mBinding.setScale(i + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        View root = mBinding.getRoot();
        Dialog dialog = getDialog();
        if (dialog instanceof AlertDialog)
            ((AlertDialog) dialog).setView(root);
        if (mRange == null)
            root.post(() -> mOngoingTask = new AnalyzeTask(this).execute());
        else goToScalePhase();
        return root;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(requireContext(), R.style.AppTheme_Dialog))
                .setTitle(R.string.picer_title)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOngoingTask != null) mOngoingTask.cancel(true);
        if (mOngoingThread != null) mOngoingThread.cancel();
    }

    // Result callbacks from AnalyzeTask.

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

    private void goToScalePhase() {
        Activity activity = getActivity();
        if (activity == null) return;
        mBinding.selectCase.setVisibility(View.VISIBLE);
        int w = mRange.right - mRange.left;
        int h = mRange.bottom - mRange.top;
        if (w <= 0 || h <= 0) {
            dismiss();
            return;
        }
        int len = w > h ? w : h;
        int maxScale = MAX_LENGTH / len;
        len = w * h;
        w = MAX_AREA / len;
        if (w < maxScale) maxScale = w;
        if (maxScale <= 0) {
            new AlertDialog.Builder(activity)
                    .setTitle(R.string.map_picer_selection_too_large)
                    .setMessage(getString(R.string.map_picer_selection_too_large_detail, MAX_LENGTH, MAX_AREA))
                    .setPositiveButton(android.R.string.ok, null)
                    .create()
                    .show();
            dismiss();
            return;
        } else if (maxScale == 1) {
            mBinding.scaleBox.setVisibility(View.GONE);
            mBinding.scaleNot.setVisibility(View.VISIBLE);
        } else {
            if (MAX_SCALE < maxScale) maxScale = MAX_SCALE;
            mBinding.scaleBox.setVisibility(View.VISIBLE);
            mBinding.scaleNot.setVisibility(View.GONE);
            mBinding.scaleSeek.setMax(maxScale - 1);
            mBinding.scaleSeek.setProgress(maxScale - 1);
        }
        stage = 1;
        mBinding.finalButton.setVisibility(View.VISIBLE);
        mBinding.finalButton.setText(R.string.picer_btn_generate);
// Minimal 16x16 px per chunk.
//        int size = area.calculateArea() * 256;
//
//        // Oops.
//        if (size > MAX_PIXELS) {
//            showFailureDialogAndDismiss(R.string.picer_failed_too_large);
//            return;
//        }
//
//        // How many levels could we scale.
//        int levels = 1;
//        for (int m = size; m <= MAX_PIXELS / 2; levels++) m *= 4;
//
//        if (levels == 1) {
//            // Cannot scale at all.
//            mBinding.scaleBox.setVisibility(View.GONE);
//            mBinding.scaleNot.setVisibility(View.VISIBLE);
//            mBinding.scaleSeek.setMax(1);
//            mBinding.scaleSeek.setProgress(1);
//        } else {
//            mBinding.scaleSeek.setMax(levels - 1);
//            mBinding.scaleSeek.setProgress(levels - 1);
//        }
//        mBinding.selectCase.setVisibility(View.VISIBLE);
//        mBinding.setArea(area);
//
//        mBinding.finalButton.setVisibility(View.VISIBLE);
//        mBinding.finalButton.setText(R.string.picer_btn_generate);
    }

    @UiThread
    private void onClickFinalButton(@NonNull View view) {
        switch (stage) {
            case 1:
                onClickGenerate();
                break;
            case 2:
                onClickSave(view);
                break;
        }
    }

    @UiThread
    void onAnalyzeDone(@NonNull Rect rect) {

        Activity activity = getActivity();
        if (activity == null) return;

        if (rangeCheck(rect)) {
            mRange = rect;
            goToScalePhase();
        } else {
            new AlertDialog.Builder(activity)
                    .setTitle(R.string.map_picer_world_too_large)
                    .setMessage(R.string.map_picer_use_selection_instead)
                    .setPositiveButton(android.R.string.ok, (dia, i) -> mOpenLongPressMenuHandler.open())
                    .create().show();
            dismiss();
        }
    }

    // Callback from .

    @UiThread
    private void onClickGenerate() {
        Activity activity = getActivity();
        if (activity == null) return;

        int scale = mBinding.scaleSeek.getProgress() + 1;
        AlertDialog dialog = UiUtil.buildProgressWaitDialog(
                activity, R.string.picer_progress_generating, dialogInterface -> dismiss());
        dialog.show();

        mBinding.finalButton.setVisibility(View.GONE);
        mBinding.selectCase.setVisibility(View.GONE);

        mOngoingThread = new GenerateThread(this, mRange, scale, Bitmap.Config.ARGB_8888, dialog);
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

        stage = 2;
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
    private void onClickSave(@NonNull View view) {
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
                        getActivity().startActivity(
                                Intent.createChooser(shareIntent,
                                        v.getContext().getString(R.string.picer_share_title)));
                    });
            snackbar.show();
            dismiss();
        }
    }

}
