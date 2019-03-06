package com.mithrilmania.blocktopograph.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.mithrilmania.blocktopograph.WorldActivityInterface;
import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.map.renderer.MapType;
import com.qozix.tileview.graphics.BitmapProvider;
import com.qozix.tileview.tiles.Tile;

import java.lang.ref.WeakReference;


public class MCTileProvider implements BitmapProvider {


    public static final int TILESIZE = 256,


    //TODO the maximum world size is way bigger than the worldsize that this app can handle (due to render glitches & rounding errors)
    //HALF_WORLDSIZE has to be a power of 2! (It must be perfectly divisible by TILESIZE, which is a power of two)
    HALF_WORLDSIZE = 1 << 20;

    public static int worldSizeInBlocks = 2 * HALF_WORLDSIZE,
            viewSizeW = worldSizeInBlocks * TILESIZE / Dimension.OVERWORLD.chunkW,
            viewSizeL = worldSizeInBlocks * TILESIZE / Dimension.OVERWORLD.chunkL;

    public final WeakReference<WorldActivityInterface> worldProvider;

    public MCTileProvider(WorldActivityInterface worldProvider) {
        this.worldProvider = new WeakReference<>(worldProvider);
    }

    public static Bitmap drawText(String text, Bitmap b, int textColor, int bgColor) {
        // Get text dimensions
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textColor);
        textPaint.setTextSize(b.getHeight() / 16f);
        StaticLayout mTextLayout = new StaticLayout(text, textPaint, b.getWidth() / 2, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

        // Create bitmap and canvas to draw to
        Canvas c = new Canvas(b);

        if (bgColor != 0) {
            // Draw background
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(bgColor);
            c.drawPaint(paint);
        }

        // Draw text
        c.save();
        c.translate(0, 0);
        mTextLayout.draw(c);
        c.restore();

        return b;
    }

    @Override
    public Bitmap getBitmap(Tile tile, Context context) {

        Bitmap bm = tile.hasBitmap() ? tile.getBitmap() : Bitmap.createBitmap(tile.getWidth(), tile.getHeight(), Bitmap.Config.RGB_565);//getRecycledBitmap();

        try {

            // 1 chunk per tile on scale 1.0
            int pixelsPerBlockW_unscaled = TILESIZE / 16;
            int pixelsPerBlockL_unscaled = TILESIZE / 16;

            float scale = tile.getDetailLevel().getScale();

            // this will be the amount of chunks in the width of one tile
            int invScale = Math.round(1f / scale);

            //scale the amount of pixels, less pixels per block if zoomed out
            int pixelsPerBlockW = Math.round(pixelsPerBlockW_unscaled * scale);
            int pixelsPerBlockL = Math.round(pixelsPerBlockL_unscaled * scale);


            // for translating to origin
            // HALF_WORLDSIZE and TILESIZE must be a power of two
            int tilesInHalfWorldW = (HALF_WORLDSIZE * pixelsPerBlockW) / TILESIZE;
            int tilesInHalfWorldL = (HALF_WORLDSIZE * pixelsPerBlockL) / TILESIZE;


            // translate tile coord to origin, multiply origin-relative-tile-coordinate with the chunks per tile
            int minChunkX = (tile.getColumn() - tilesInHalfWorldW) * invScale;
            int minChunkZ = (tile.getRow() - tilesInHalfWorldL) * invScale;
            int maxChunkX = minChunkX + invScale;
            int maxChunkZ = minChunkZ + invScale;

            WorldActivityInterface worldProvider = this.worldProvider.get();
            Dimension dimension = worldProvider.getDimension();

            MapType mapType = (MapType) tile.getDetailLevel().getLevelType();
            if (mapType == null) return null;


            int x, z, pX, pY;
            String tileTxt;

            //check if the tile is not aligned with its inner chunks
            //hacky: it must be a single chunk that is to big for the tile, render just the visible part, easy.


            tileTxt = "(" + (minChunkX * 16) + "; " + (minChunkZ * 16) + ")";


            int pixelsPerChunkW = pixelsPerBlockW * 16;
            int pixelsPerChunkL = pixelsPerBlockL * 16;

            Canvas canvas = new Canvas(bm);
            Paint paint = new Paint();

            WorldData worldData = worldProvider.getWorld().getWorldData();

            for (z = minChunkZ, pY = 0; z < maxChunkZ; z++, pY += pixelsPerChunkL)
                for (x = minChunkX, pX = 0; x < maxChunkX; x++, pX += pixelsPerChunkW) {

                    Chunk chunk = worldData.getChunk(x, z, dimension);
                    if (chunk.isError()) {
                        MapType.ERROR.renderer.renderToBitmap(chunk, canvas, dimension,
                                x, z, pX, pY, pixelsPerBlockW, pixelsPerBlockL, paint, worldData);
                        continue;
                    }
                    MapType.CHESS.renderer.renderToBitmap(chunk, canvas, dimension,
                            x, z, pX, pY, pixelsPerBlockW, pixelsPerBlockL, paint, worldData);
                    if (chunk.isVoid()) continue;
                    try {
                        mapType.renderer.renderToBitmap(chunk, canvas, dimension, x, z,
                                pX, pY, pixelsPerBlockW, pixelsPerBlockL, paint, worldData);

                    } catch (Exception e) {

                        MapType.ERROR.renderer.renderToBitmap(chunk, canvas, dimension,
                                x, z, pX, pY, pixelsPerBlockW, pixelsPerBlockL, paint, worldData);
                        e.printStackTrace();

                    }

                }


            //load all those markers with an async task, this task publishes its progress,
            // the UI thread picks it up and renders the markers
            if (worldProvider.getShowMarkers())
                new MarkerAsyncTask(worldProvider, minChunkX, minChunkZ, maxChunkX, maxChunkZ, dimension).execute();


            //draw the grid
            if (worldProvider.getShowGrid()) {

                //draw tile-edges white
                for (int i = 0; i < TILESIZE; i++) {

                    //horizontal edges
                    bm.setPixel(i, 0, Color.WHITE);
                    bm.setPixel(i, TILESIZE - 1, Color.WHITE);

                    //vertical edges
                    bm.setPixel(0, i, Color.WHITE);
                    bm.setPixel(TILESIZE - 1, i, Color.WHITE);

                }

                //draw tile coordinates on top of bitmap
                drawText(tileTxt, bm, Color.WHITE, 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return bm;
    }


}
