package com.mithrilmania.blocktopograph;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

public class MenuHelper {

    public interface MenuContext {

        Context getContext();

        boolean propagateOnOptionsItemSelected(MenuItem menuItem);

    }

    public static boolean onOptionsItemSelected(MenuContext menuContext, MenuItem item) {

        Context ctx = menuContext.getContext();

        //some text pop-up dialogs, some with simple HTML tags.
        switch (item.getItemId()) {
            case R.id.action_about: {

                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                TextView msg = new TextView(ctx);
                msg.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                float dpi = ctx.getResources().getDisplayMetrics().density;
                msg.setPadding((int) (19 * dpi), (int) (5 * dpi), (int) (14 * dpi), (int) (5 * dpi));
                msg.setMaxLines(20);
                msg.setMovementMethod(LinkMovementMethod.getInstance());
                msg.setText(R.string.app_about);
                builder.setView(msg)
                        .setTitle(R.string.action_about)
                        .setCancelable(true)
                        .setNeutralButton(android.R.string.ok, null)
                        .show();

                return true;
            }
            case R.id.action_help: {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                TextView msg = new TextView(ctx);
                float dpi = ctx.getResources().getDisplayMetrics().density;
                msg.setPadding((int) (19 * dpi), (int) (5 * dpi), (int) (14 * dpi), (int) (5 * dpi));
                msg.setMaxLines(20);
                msg.setMovementMethod(LinkMovementMethod.getInstance());
                msg.setText(R.string.app_help);
                builder.setView(msg)
                        .setTitle(R.string.action_help)
                        .setCancelable(true)
                        .setNeutralButton(android.R.string.ok, null)
                        .show();

                return true;
            }
            case R.id.action_changelog: {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                TextView msg = new TextView(ctx);
                float dpi = ctx.getResources().getDisplayMetrics().density;
                msg.setPadding((int) (19 * dpi), (int) (5 * dpi), (int) (14 * dpi), (int) (5 * dpi));
                msg.setMaxLines(20);
                msg.setMovementMethod(LinkMovementMethod.getInstance());
                String content = String.format(ctx.getResources().getString(R.string.app_changelog), BuildConfig.VERSION_NAME);
                //noinspection deprecation
                msg.setText(Html.fromHtml(content));
                builder.setView(msg)
                        .setTitle(R.string.action_changelog)
                        .setCancelable(true)
                        .setNeutralButton(android.R.string.ok, null)
                        .show();

                return true;
            }
            default: {
                return menuContext.propagateOnOptionsItemSelected(item);
            }
        }
    }

}
