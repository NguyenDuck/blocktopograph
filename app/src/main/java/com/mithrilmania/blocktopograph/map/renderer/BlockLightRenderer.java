package com.mithrilmania.blocktopograph.map.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.ChunkManager;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.chunk.terrain.TerrainChunkData;
import com.mithrilmania.blocktopograph.map.Dimension;


public class BlockLightRenderer implements MapRenderer {

    /**
     * Render a single chunk to provided bitmap (bm)
     *
     * @param chunk        ChunkManager, provides chunks, which provide chunk-data
     * @param canvas       Bitmap to render to
     * @param dimension    Mapped dimension
     * @param chunkX       X chunk coordinate (x-block coord / Chunk.WIDTH)
     * @param chunkZ       Z chunk coordinate (z-block coord / Chunk.LENGTH)
     * @param pX           texture X pixel coord to start rendering to
     * @param pY           texture Y pixel coord to start rendering to
     * @param pW           width (X) of one block in pixels
     * @param pL           length (Z) of one block in pixels
     * @param paint
     * @param version
     * @param chunkManager
     * @return bm is returned back
     * @throws Version.VersionException when the version of the chunk is unsupported.
     */
    public void renderToBitmap(Chunk chunk, Canvas canvas, Dimension dimension, int chunkX, int chunkZ, int pX, int pY, int pW, int pL, Paint paint, Version version, ChunkManager chunkManager) throws Version.VersionException {

        int x, y, z, subChunk, color, i, j, tX, tY;

        //render width in blocks
        int rW = 16;
        int[] light = new int[rW * 16];

        for (subChunk = 0; subChunk < version.subChunks; subChunk++) {
            TerrainChunkData data = chunk.getTerrain((byte) subChunk);
            if (data == null || !data.loadTerrain()) break;

            for (z = 0; z < 16; z++) {
                for (x = 0; x < 16; x++) {
                    for (y = 0; y < version.subChunkHeight; y++) {
                        light[(z * rW) + x] += data.getBlockLightValue(x, y, z) & 0xff;
                    }
                }
            }
        }

        int l;
        for (z = 0, tY = pY; z < 16; z++, tY += pL) {
            for (x = 0, tX = pX; x < 16; x++, tX += pW) {

                l = light[(z * rW) + x];
                l = l < 0 ? 0 : ((l > 0xff) ? 0xff : l);

                color = (l << 16) | (l << 8) | (l) | 0xff000000;


                paint.setColor(color);
                canvas.drawRect(new Rect(tX, tY, tX + pW, tY + pL), paint);

            }

        }
    }

}
