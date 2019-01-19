package com.mithrilmania.blocktopograph.map.renderer;

import android.graphics.Bitmap;

import com.mithrilmania.blocktopograph.chunk.ChunkManager;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.map.Dimension;


public class DebugRenderer implements MapRenderer {

    /**
     * Render a single chunk to provided bitmap (bm)
     *
     * @param cm        ChunkManager, provides chunks, which provide chunk-data
     * @param bm        Bitmap to render to
     * @param dimension Mapped dimension
     * @param chunkX    X chunk coordinate (x-block coord / Chunk.WIDTH)
     * @param chunkZ    Z chunk coordinate (z-block coord / Chunk.LENGTH)
     * @param pX        texture X pixel coord to start rendering to
     * @param pY        texture Y pixel coord to start rendering to
     * @param pW        width (X) of one block in pixels
     * @param pL        length (Z) of one block in pixels
     * @return bm is returned back
     * @throws Version.VersionException when the version of the chunk is unsupported.
     */
    public Bitmap renderToBitmap(ChunkManager cm, Bitmap bm, Dimension dimension, int chunkX, int chunkZ, int pX, int pY, int pW, int pL) throws Version.VersionException {

        int x, z, i, j, tX, tY;

        int offsetX = chunkX * dimension.chunkW;
        int offsetZ = chunkZ * dimension.chunkL;

        for (z = 0, tY = pY; z < 16; z++, tY += pL) {
            for (x = 0, tX = pX; x < 16; x++, tX += pW) {
                for (i = 0; i < pL; i++) {
                    for (j = 0; j < pW; j++) {
                        bm.setPixel(tX + j, tY + i, 0xff000000 | ((offsetX + x) ^ (offsetZ + z)));
                    }
                }
            }
        }


        return bm;
    }


}