package com.mithrilmania.blocktopograph.map.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.mithrilmania.blocktopograph.Log;

import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.ChunkManager;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.chunk.terrain.TerrainChunkData;
import com.mithrilmania.blocktopograph.map.Block;
import com.mithrilmania.blocktopograph.map.Dimension;


public class SatelliteRenderer implements MapRenderer {

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
        for (z = 0, tY = pY; z < 16; z++, tY += pL) {
            for (x = 0, tX = pX; x < 16; x++, tX += pW) {

                y = data.getHeightMapValue(x, z);

                color = getColumnColour(chunk, data, x, y, z,
                        (x == 0) ? (west ? dataW.getHeightMapValue(dimension.chunkW - 1, z) : y)//chunk edge
                                : data.getHeightMapValue(x - 1, z),//within chunk
                        (z == 0) ? (north ? dataN.getHeightMapValue(x, dimension.chunkL - 1) : y)//chunk edge
                                : data.getHeightMapValue(x, z - 1)//within chunk
                );
                paint.setColor(color);
                canvas.drawRect(new Rect(tX, tY, tX + pW, tY + pL), paint);


            }
        }

        return bm;
    }

    //calculate color of one column
    private static int getColumnColour(Chunk chunk, TerrainChunkData floorData, int x, int y, int z, int heightW, int heightN) throws Version.VersionException {
        float a = 1f;
        float r = 0f;
        float g = 0f;
        float b = 0f;

        // extract colour components as normalized doubles, from ARGB format
        float biomeR = (float) (floorData.getGrassR(x, z) & 0xff) / 255f;
        float biomeG = (float) (floorData.getGrassG(x, z) & 0xff) / 255f;
        float biomeB = (float) (floorData.getGrassB(x, z) & 0xff) / 255f;

        float blendR, blendG, blendB;

        float blockA, blockR, blockG, blockB;


        Block block;
        int id, meta;

        Version cVersion = chunk.getVersion();
        int realY = y;
        int offset = y % cVersion.subChunkHeight;
        int subChunk = y / cVersion.subChunkHeight;

        TerrainChunkData data;

        subChunkLoop:
        for (; subChunk >= 0; subChunk--) {

            data = chunk.getTerrain((byte) subChunk);
            if (data == null || !data.loadTerrain()) {
                //start at the top of the next chunk! (current offset might differ)
                offset = cVersion.subChunkHeight - 1;
                continue;
            }

            for (y = offset; y >= 0; y--) {

                id = data.getBlockTypeId(x, y, z) & 0xff;

                if (id == 0) continue;//skip air blocks

                meta = data.getBlockData(x, y, z) & 0xff;
                block = Block.getBlock(id, meta);

                //try the default meta value: 0
                if (block == null) block = Block.getBlock(id, 0);


                //TODO log null blocks to debug missing blocks
                if (block == null) {
                    Log.w("UNKNOWN block: id: " + id + " meta: " + meta);
                    continue;
                }

                // no need to process block if it is fully transparent
                if (block.color == null || block.color.alpha == 0) continue;

                blockR = block.color.red / 255f;
                blockG = block.color.green / 255f;
                blockB = block.color.blue / 255f;
                blockA = block.color.alpha / 255f;

                // alpha blend and multiply
                blendR = a * blockA * blockR;
                blendG = a * blockA * blockG;
                blendB = a * blockA * blockB;

                //blend biome-colored blocks
                if (block.hasBiomeShading) {
                    blendR *= biomeR;
                    blendG *= biomeG;
                    blendB *= biomeB;
                }

                r += blendR;
                g += blendG;
                b += blendB;
                a *= 1f - blockA;

                // break when an opaque block is encountered
                if (block.color.alpha == 0xff) {
                    break subChunkLoop;
                }
            }

            //start at the top of the next chunk! (current offset might differ)
            offset = cVersion.subChunkHeight - 1;
        }

        //set y to the "real" y; consider all sub-chunks as a stack of chunks.
        y = realY;

        //height shading (based on slopes in terrain; height diff)
        float heightShading = getHeightShading(y, heightW, heightN);

        //go back to "surface"
        y++;

        TerrainChunkData surfaceChunk = chunk.getTerrain((byte) (y / cVersion.subChunkHeight));
        //light sources
        int lightValue = (surfaceChunk != null && surfaceChunk.loadTerrain())
                ? (surfaceChunk.getBlockLightValue(x, y % cVersion.subChunkHeight, z) & 0xff)
                : 0;
        float lightShading = (float) lightValue / 15f + 1;

        //mix shading
        float shading = heightShading * lightShading;

        //low places just get darker
        //shading *= Math.max(Math.min(y / 40f, 1f), 0.2f);//shade ravines & caves, minimum *0.2 to keep some color

        // apply the shading
        r = Math.min(Math.max(0f, r * shading), 1f);
        g = Math.min(Math.max(0f, g * shading), 1f);
        b = Math.min(Math.max(0f, b * shading), 1f);


        // now we have our final RGB values as floats, convert to a packed ARGB pixel.
        return 0xff000000 |
                ((((int) (r * 255f)) & 0xff) << 16) |
                ((((int) (g * 255f)) & 0xff) << 8) |
                (((int) (b * 255f)) & 0xff);
    }

    // shading Amp, possible range: [0, 2] (or use negative for reverse shading)
    private static final float shadingAmp = 0.8f;

    public static float getHeightShading(int height, int heightW, int heightN) {
        int samples = 0;
        float heightDiff = 0;

        if (heightW > 0) {
            heightDiff += height - heightW;
            samples++;
        }

        if (heightN > 0) {
            heightDiff += height - heightN;
            samples++;
        }

        heightDiff *= Math.pow(1.05f, samples);

        // emphasize small differences in height, but as the difference in height increases, don't increase so much
        return ((float) (Math.atan(heightDiff) / Math.PI) * shadingAmp) + 1f;
    }

}