package com.mithrilmania.blocktopograph.map.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.ChunkManager;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.chunk.terrain.TerrainChunkData;
import com.mithrilmania.blocktopograph.map.Dimension;


public class GrassRenderer implements MapRenderer {

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

        //the bottom sub-chunk is sufficient to get grass data.
        TerrainChunkData data = chunk.getTerrain((byte) 0);
        if (data == null || !data.load2DData())
            throw new RuntimeException();

        int x, z, color, tX, tY;

        for (z = 0, tY = pY; z < 16; z++, tY += pL) {
            for (x = 0, tX = pX; x < 16; x++, tX += pW) {

                color = ((data.getGrassR(x, z) & 0xff) << 16) | ((data.getGrassG(x, z) & 0xff) << 8) | (data.getGrassB(x, z) & 0xff) | 0xff000000;

                paint.setColor(color);
                canvas.drawRect(new Rect(tX, tY, tX + pW, tY + pL), paint);


            }
        }
    }

}
