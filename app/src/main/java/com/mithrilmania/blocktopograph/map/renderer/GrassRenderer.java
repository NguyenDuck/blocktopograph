package com.mithrilmania.blocktopograph.map.renderer;

import android.graphics.Bitmap;

import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.ChunkManager;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.chunk.terrain.TerrainChunkData;
import com.mithrilmania.blocktopograph.map.Dimension;


public class GrassRenderer implements MapRenderer {

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

        Chunk chunk = cm.getChunk(chunkX, chunkZ, dimension);
        Version cVersion = chunk.getVersion();

        if (cVersion == Version.ERROR)
            return MapType.ERROR.renderer.renderToBitmap(cm, bm, dimension, chunkX, chunkZ, pX, pY, pW, pL);
        if (cVersion == Version.NULL)
            return MapType.CHESS.renderer.renderToBitmap(cm, bm, dimension, chunkX, chunkZ, pX, pY, pW, pL);

        //the bottom sub-chunk is sufficient to get grass data.
        TerrainChunkData data = chunk.getTerrain((byte) 0);
        if (data == null || !data.load2DData())
            return MapType.CHESS.renderer.renderToBitmap(cm, bm, dimension, chunkX, chunkZ, pX, pY, pW, pL);


        int x, z, color, i, j, tX, tY;

        for (z = 0, tY = pY; z < 16; z++, tY += pL) {
            for (x = 0, tX = pX; x < 16; x++, tX += pW) {

                color = ((data.getGrassR(x, z) & 0xff) << 16) | ((data.getGrassG(x, z) & 0xff) << 8) | (data.getGrassB(x, z) & 0xff) | 0xff000000;

                for (i = 0; i < pL; i++) {
                    for (j = 0; j < pW; j++) {
                        bm.setPixel(tX + j, tY + i, color);
                    }
                }


            }
        }

        return bm;
    }

}
