package com.mithrilmania.blocktopograph.map.picer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.map.edit.RectEditTarget;
import com.mithrilmania.blocktopograph.map.renderer.MapRenderer;

import java.lang.ref.WeakReference;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class GenerateThread extends Thread {

    private final Rect area;
    private final WeakReference<PicerFragment> owner;
    private final int scale;
    private final Bitmap.Config config;
    private final WeakReference<AlertDialog> dialog;

    private boolean cancelled;

    GenerateThread(PicerFragment owner, Rect area, int scale, Bitmap.Config config,
                   AlertDialog dialog) {
        this.owner = new WeakReference<>(owner);
        this.area = area;
        this.scale = scale;
        this.config = config;
        this.dialog = new WeakReference<>(dialog);
    }

    @Override
    public void run() {

        PicerFragment owner = this.owner.get();
        if (owner == null) return;

        Dimension dimension = owner.mDimension;
        MapRenderer renderer = dimension.defaultMapType.renderer;
        WorldData wdata = owner.mWorld.getWorldData();

        int width = area.right - area.left + 1;
        int height = area.bottom - area.top + 1;

        Bitmap bitmap = Bitmap.createBitmap(width * scale, height * scale, config);
        Canvas canvas = new Canvas(bitmap);

        int poolSize = 5;

        ExecutorService executor =
                new ThreadPoolExecutor(poolSize, poolSize, 0, TimeUnit.MILLISECONDS,
                        new ArrayBlockingQueue<>(poolSize << 1),
                        new ThreadPoolExecutor.CallerRunsPolicy());

        ThreadLocal<Paint> paintSub = new ThreadLocal<Paint>() {
            @NonNull
            @Override
            protected Paint initialValue() {
                return new Paint();
            }
        };

        new RectEditTarget(wdata, area, dimension).forEachChunk(
                (chunk, fromX, toX, fromY, toY, fromZ, toZ) -> {
                    if (cancelled) {
                        executor.shutdownNow();
                        return 0;
                    }
                    executor.execute(() -> {
                        try {
                            renderer.renderToBitmap(chunk, canvas, dimension, chunk.mChunkX, chunk.mChunkZ,
                                    (chunk.mChunkX * 16 - area.left) * scale,
                                    (chunk.mChunkZ * 16 - area.top) * scale,
                                    scale, scale, paintSub.get(), wdata);
                        } catch (Exception e) {
                            Log.d(this, e);
                        }
                    });
                    return 0;
                });
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return;
        }

        owner = this.owner.get();
        if (owner == null) return;

        FragmentActivity activity = owner.getActivity();
        if (activity != null)
            activity.runOnUiThread(() -> this.owner.get().onGenerationDone(bitmap, dialog.get()));

        owner.mOngoingThread = null;
    }

    void cancel() {
        cancelled = true;
        interrupt();
    }
}