package com.mithrilmania.blocktopograph.map.picer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Pair;

import java.lang.ref.WeakReference;

public class PreviewGenerateThread extends AsyncTask<Area, Pair<Integer, Bitmap>, Void> {

    private int MAX_DIM = 800;
    private final WeakReference<PicerFragment> owner;

    PreviewGenerateThread(PicerFragment owner) {
        this.owner = new WeakReference<>(owner);
    }

    @Override
    protected Void doInBackground(Area... areas) {
        assert areas != null;
        for (int i = 0; i < areas.length; i++) {
            Bitmap bitmap = null;
            flow:
            {
                Area area = areas[i];
                int width = area.width();
                int height = area.height();
                // Wow man that's way too large! 'll be broken~
                // ( What the hell are you thinking about )
                if (width > MAX_DIM || height > MAX_DIM) break flow;
                // Scale up unless reach limit.
                int scale = MAX_DIM / Math.max(width, height);
                bitmap = Bitmap.createBitmap(
                        width * scale, height * scale, Bitmap.Config.ALPHA_8);
                Canvas canvas = new Canvas(bitmap);
                Paint paint = new Paint();
                paint.setAlpha(127);
                for (Area.Member mem : area.getList()) {
                    int x = mem.x - area.mMinX;
                    int y = mem.z - area.mMinZ;
                    canvas.drawRect(new Rect(
                            x * scale, y * scale,
                            (x + 1) * scale, (y + 1) * scale), paint);
                }
            }
            //noinspection unchecked
            publishProgress(new Pair<>(i, bitmap));
        }
        return null;
    }

    @SafeVarargs
    @Override
    protected final void onProgressUpdate(Pair<Integer, Bitmap>... values) {
       //
    }
}
