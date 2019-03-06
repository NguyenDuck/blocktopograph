package com.mithrilmania.blocktopograph.map.picer;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.util.IoUtil;

import java.io.File;
import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

class SaveTask extends AsyncTask<Bitmap, Void, File> {

    private final WeakReference<PicerFragment> owner;
    private WeakReference<AlertDialog> dialog;
    private final String worldName;

    SaveTask(PicerFragment owner, String worldName) {
        this.owner = new WeakReference<>(owner);
        this.worldName = worldName;
    }

    @Override
    protected void onPreExecute() {
        PicerFragment owner = this.owner.get();
        if (owner == null) return;
        Activity activity = owner.getActivity();
        if (activity == null) return;
        AlertDialog dia = new AlertDialog.Builder(activity)
                .setView(R.layout.general_wait)
                .setCancelable(false)
                .create();
        dia.setCanceledOnTouchOutside(false);
        dia.show();
        dialog = new WeakReference<>(dia);
    }

    @Override
    protected File doInBackground(Bitmap... bitmaps) {
        // assert bitmaps != null && bitmaps.length == 1;
        Bitmap bmp = bitmaps[0];
        assert bmp != null;
        return IoUtil.saveBitmap(bmp, Bitmap.CompressFormat.PNG, 0,
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                worldName + "_map", true);
    }

    @Override
    protected void onPostExecute(@Nullable File result) {
        AlertDialog dia;
        if (dialog != null && (dia = dialog.get()) != null) dia.dismiss();
        PicerFragment owner = this.owner.get();
        if (owner == null) return;
        owner.onSavedBitmap(result);
    }
}