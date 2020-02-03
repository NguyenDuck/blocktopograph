package com.mithrilmania.blocktopograph.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.ColorUtils;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.block.KnownBlockRepr;
import com.mithrilmania.blocktopograph.block.ListingBlock;
import com.mithrilmania.blocktopograph.databinding.GeneralWaitBinding;
import com.mithrilmania.blocktopograph.map.Biome;


public final class UiUtil {

    public static void toastError(@NonNull Context context) {
        Toast.makeText(context, R.string.error_general, Toast.LENGTH_SHORT).show();
    }

    public static void toast(@NonNull Context context, @NonNull String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void toast(@NonNull Context context, @StringRes int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    public static void snackError(@NonNull View view) {
        Snackbar.make(view, R.string.error_general, Snackbar.LENGTH_SHORT).show();
    }

    public static void snack(@NonNull View view, @NonNull String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    public static void snack(@NonNull Activity activity, @NonNull String text) {
        snack(activity.getWindow().getDecorView(), text);
    }

    public static void snack(@NonNull View view, @StringRes int resId) {
        Snackbar.make(view, resId, Snackbar.LENGTH_SHORT).show();
    }

    public static void snack(@NonNull Activity activity, @StringRes int resId) {
        snack(activity.getWindow().getDecorView(), resId);
    }

    public static void blendBlockColor(@NonNull View view, KnownBlockRepr block) {
        Drawable drawable = view.getBackground();
        if (!(drawable instanceof GradientDrawable)) return;
        GradientDrawable gradientDrawable = (GradientDrawable) drawable;
        int res = (block.id == 0) ? 0 : ColorUtils.blendARGB(block.color, 0x7f7f7f7f, 0.5f);
        gradientDrawable.setColor(res);
    }

    public static void blendBlockColor(@NonNull View view, ListingBlock block) {
        Drawable drawable = view.getBackground();
        if (!(drawable instanceof GradientDrawable)) return;
        GradientDrawable gradientDrawable = (GradientDrawable) drawable;
        int color = block.getColor();
        if (color != Color.TRANSPARENT)
            color = ColorUtils.blendARGB(color, 0x7f7f7f7f, 0.5f);
        gradientDrawable.setColor(color);
    }

    public static void blendBlockColor(@NonNull View view, Biome biome) {
        Drawable drawable = view.getBackground();
        if (!(drawable instanceof GradientDrawable)) return;
        GradientDrawable gradientDrawable = (GradientDrawable) drawable;
        int res = ColorUtils.blendARGB(biome.color.asARGB(), 0x7f7f7f7f, 0.5f);
        gradientDrawable.setColor(res);
    }

    @Nullable
    public static Integer readIntFromView(@NonNull EditText editText, boolean emptyAsZero) {
        String string = editText.getText().toString();
        if (emptyAsZero && string.isEmpty()) return 0;
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static int readIntFromView(@NonNull EditText editText) {
        String string = editText.getText().toString();
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int readIntFromViewWithDefault(@NonNull EditText editText, int defaultVal) {
        String string = editText.getText().toString();
        if (string.trim().isEmpty()) return defaultVal;
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    public static AlertDialog buildProgressWaitDialog(
            @NonNull Context context, @StringRes int text,
            @Nullable DialogInterface.OnCancelListener onCancelListener) {
        GeneralWaitBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.general_wait, null, false
        );
        binding.setText(text);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(binding.getRoot())
                .setCancelable(onCancelListener != null)
                .create();
        if (onCancelListener != null) dialog.setOnCancelListener(onCancelListener);
        else dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static float dpToPx(@NonNull Context context, float dp) {
        return context.getResources().getDisplayMetrics().density * dp;
    }

    public static int dpToPxInt(@NonNull Context context, int dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp);
    }
}
