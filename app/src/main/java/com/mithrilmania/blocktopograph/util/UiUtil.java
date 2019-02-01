package com.mithrilmania.blocktopograph.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.map.Block;

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

    @NonNull
    public static AlertDialog buildWaitDialog(@NonNull Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(R.layout.general_wait)
                .setCancelable(false)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static void blendBlockColor(View view, Block block) {
        Drawable drawable = view.getBackground();
        if (!(drawable instanceof GradientDrawable)) return;
        GradientDrawable gradientDrawable = (GradientDrawable) drawable;
        Color color = block.color;
        int res = ColorUtils.blendARGB(color.asARGB(), 0x7f7f7f7f, 0.5f);
        gradientDrawable.setColor(res);
    }
}
