package com.mithrilmania.blocktopograph.map.renderer;

import android.graphics.Bitmap;

import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.ChunkManager;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.chunk.terrain.TerrainChunkData;
import com.mithrilmania.blocktopograph.map.Dimension;



public class BlockLightRenderer implements MapRenderer {

    /**
     * Render a single chunk to provided bitmap (bm)
     * @param cm ChunkManager, provides chunks, which provide chunk-data
     * @param bm Bitmap to render to
     * @param dimension Mapped dimension
     * @param chunkX X chunk coordinate (x-block coord / Chunk.WIDTH)
     * @param chunkZ Z chunk coordinate (z-block coord / Chunk.LENGTH)
     * @param bX begin block X coordinate, relative to chunk edge
     * @param bZ begin block Z coordinate, relative to chunk edge
     * @param eX end block X coordinate, relative to chunk edge
     * @param eZ end block Z coordinate, relative to chunk edge
     * @param pX texture X pixel coord to start rendering to
     * @param pY texture Y pixel coord to start rendering to
     * @param pW width (X) of one block in pixels
     * @param pL length (Z) of one block in pixels
     * @return bm is returned back
     *
     * @throws Version.VersionException when the version of the chunk is unsupported.
     */
    public Bitmap renderToBitmap(ChunkManager cm, Bitmap bm, Dimension dimension, int chunkX, int chunkZ, int bX, int bZ, int eX, int eZ, int pX, int pY, int pW, int pL) throws Version.VersionException {

        Chunk chunk = cm.getChunk(chunkX, chunkZ);
        Version cVersion = chunk.getVersion();

        if(cVersion == Version.ERROR) return MapType.ERROR.renderer.renderToBitmap(cm, bm, dimension, chunkX, chunkZ, bX, bZ, eX, eZ, pX, pY, pW, pL);
        if(cVersion == Version.NULL) return MapType.CHESS.renderer.renderToBitmap(cm, bm, dimension, chunkX, chunkZ, bX, bZ, eX, eZ, pX, pY, pW, pL);


        int x, y, z, subChunk, color, i, j, tX, tY;

        //render width in blocks
        int rW = eX - bX;
        int[] light = new int[rW * (eZ - bZ)];

        for(subChunk = 0; subChunk < cVersion.subChunks; subChunk++) {
            TerrainChunkData data = chunk.getTerrain((byte) subChunk);
            if (data == null || !data.loadTerrain()) break;

            for (z = bZ; z < eZ; z++) {
                for (x = bX; x < eX; x++) {
                    for (y = 0; y < cVersion.subChunkHeight; y++) {
                        light[((z - bZ) * rW) + (x - bX)] += data.getBlockLightValue(x, y, z) & 0xff;
                    }
                }
            }
        }

        int l;
        for (z = bZ, tY = pY; z < eZ; z++, tY += pL) {
            for (x = bX, tX = pX; x < eX; x++, tX += pW) {

                l = light[((z - bZ) * rW) + (x - bX)];
                l = l < 0 ? 0 : ((l > 0xff) ? 0xff : l);

                color = (l << 16) | (l << 8) | (l) | 0xff000000;

                for(i = 0; i < pL; i++){
                    for(j = 0; j < pW; j++){
                        bm.setPixel(tX + j, tY + i, color);
                    }
                }

            }

        }

        if(subChunk == 0) return MapType.CHESS.renderer.renderToBitmap(cm, bm, dimension, chunkX, chunkZ, bX, bZ, eX, eZ, pX, pY, pW, pL);

        return bm;
    }

}
