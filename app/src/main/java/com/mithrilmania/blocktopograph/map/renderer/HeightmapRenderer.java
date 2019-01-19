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


public class HeightmapRenderer implements MapRenderer {

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

        //the bottom sub-chunk is sufficient to get heightmap data.
        TerrainChunkData data = chunk.getTerrain((byte) 0);
        if (data == null || !data.load2DData())
            return MapType.CHESS.renderer.renderToBitmap(cm, bm, dimension, chunkX, chunkZ, pX, pY, pW, pL);


        TerrainChunkData dataW = cm.getChunk(chunkX - 1, chunkZ, dimension).getTerrain((byte) 0);
        TerrainChunkData dataN = cm.getChunk(chunkX, chunkZ - 1, dimension).getTerrain((byte) 0);

        boolean west = dataW != null && dataW.load2DData(),
                north = dataN != null && dataN.load2DData();

        int x, y, z, color, i, j, tX, tY;
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint();
        int yW, yN;
        int r, g, b;
        float yNorm, yNorm2, heightShading;

        for (z = 0, tY = pY; z < 16; z++, tY += pL) {
            for (x = 0, tX = pX; x < 16; x++, tX += pW) {


                //smooth step function: 6x^5 - 15x^4 + 10x^3
                y = data.getHeightMapValue(x, z);

                yNorm = (float) y / (float) dimension.chunkH;
                yNorm2 = yNorm * yNorm;
                yNorm = ((6f * yNorm2) - (15f * yNorm) + 10f) * yNorm2 * yNorm;

                yW = (x == 0) ? (west ? dataW.getHeightMapValue(dimension.chunkW - 1, z) : y)//chunk edge
                        : data.getHeightMapValue(x - 1, z);//within chunk
                yN = (z == 0) ? (north ? dataN.getHeightMapValue(x, dimension.chunkL - 1) : y)//chunk edge
                        : data.getHeightMapValue(x, z - 1);//within chunk

                heightShading = SatelliteRenderer.getHeightShading(y, yW, yN);


                r = (int) (yNorm * heightShading * 256f);
                g = (int) (70f * heightShading);
                b = (int) (256f * (1f - yNorm) / (yNorm + 1f));

                r = r < 0 ? 0 : r > 255 ? 255 : r;
                g = g < 0 ? 0 : g > 255 ? 255 : g;
                b = b < 0 ? 0 : b > 255 ? 255 : b;


                color = (r << 16) | (g << 8) | b | 0xff000000;

                paint.setColor(color);
                canvas.drawRect(new Rect(tX, tY, tX + pW, tY + pL), paint);


            }
        }

        return bm;
    }

}
