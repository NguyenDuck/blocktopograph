package com.mithrilmania.blocktopograph.util;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.ColorUtils;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.databinding.GeneralWaitBinding;
import com.mithrilmania.blocktopograph.map.Biome;
import com.mithrilmania.blocktopograph.map.KnownBlock;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class UiUtil {

    public static void toastError(@NonNull Context context) {
        Toast.makeText(context, R.string.error_general, Toast.LENGTH_SHORT).show();
    }

    public static void toast(@NonNull Context context, @NonNull String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void snackError(@NonNull View view) {
        Snackbar.make(view, R.string.error_general, Snackbar.LENGTH_SHORT).show();
    }

    public static void snack(@NonNull View view, @NonNull String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    public static void blendBlockColor(@NotNull View view, KnownBlock block) {
        Drawable drawable = view.getBackground();
        if (!(drawable instanceof GradientDrawable)) return;
        GradientDrawable gradientDrawable = (GradientDrawable) drawable;
        ColorWrapper color = block.color;
        int res = (block.id == 0) ? 0 : ColorUtils.blendARGB(color.asARGB(), 0x7f7f7f7f, 0.5f);
        gradientDrawable.setColor(res);
    }

    public static void blendBlockColor(@NotNull View view, Biome biome) {
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
            @NotNull Context context, @StringRes int text,
            @Nullable DialogInterface.OnCancelListener onCancelListener) {
        GeneralWaitBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.general_wait, null, false
        );
        binding.setText(text);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(binding.getRoot())
                .setCancelable(onCancelListener != null)
                .setOnCancelListener(onCancelListener)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static float dpToPx(@NotNull Context context, float dp) {
        return context.getResources().getDisplayMetrics().density * dp;
    }

    public static int dpToPxInt(@NotNull Context context, int dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp);
    }
}
