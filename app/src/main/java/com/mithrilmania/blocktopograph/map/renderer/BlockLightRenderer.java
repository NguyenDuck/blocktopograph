package com.mithrilmania.blocktopograph.map.renderer;

import android.graphics.Bitmap;
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


        int x, y, z, subChunk, color, i, j, tX, tY;
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint();

        //render width in blocks
        int rW = 16;
        int[] light = new int[rW * 16];

        for (subChunk = 0; subChunk < cVersion.subChunks; subChunk++) {
            TerrainChunkData data = chunk.getTerrain((byte) subChunk);
            if (data == null || !data.loadTerrain()) break;

            for (z = 0; z < 16; z++) {
                for (x = 0; x < 16; x++) {
                    for (y = 0; y < cVersion.subChunkHeight; y++) {
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

        if (subChunk == 0)
            return MapType.CHESS.renderer.renderToBitmap(cm, bm, dimension, chunkX, chunkZ, pX, pY, pW, pL);

        return bm;
    }

}
