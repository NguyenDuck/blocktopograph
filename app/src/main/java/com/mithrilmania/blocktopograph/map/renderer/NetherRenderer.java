package com.mithrilmania.blocktopograph.map.renderer;



import android.graphics.Bitmap;
import com.mithrilmania.blocktopograph.Log;

import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.ChunkManager;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.chunk.terrain.TerrainChunkData;
import com.mithrilmania.blocktopograph.map.Block;
import com.mithrilmania.blocktopograph.map.Dimension;


public class NetherRenderer implements MapRenderer {

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

        //bottom chunk must be present
        TerrainChunkData floorData = chunk.getTerrain((byte) 0);
        if(floorData == null || !floorData.load2DData()) return MapType.CHESS.renderer.renderToBitmap(cm, bm, dimension, chunkX, chunkZ, bX, bZ, eX, eZ, pX, pY, pW, pL);


        Chunk chunkW = cm.getChunk(chunkX - 1, chunkZ);
        Chunk chunkN = cm.getChunk(chunkX, chunkZ-1);

        TerrainChunkData data;

        float shading, shadingSum, rf, gf, bf, af, a, blendR, blendG, blendB, sumRf, sumGf, sumBf;
        int layers;
        int caveceil, cavefloor, cavefloorW, cavefloorN;
        int x, y, z, color, i, j, tX, tY, r, g, b;
        Block block;
        int id, meta;
        int worth;
        int lightValue;
        float heightShading, lightShading, sliceShading, avgShading;

        int offset;
        int stop;
        int subChunk;
        int stopSubChunk;

        for (z = bZ, tY = pY ; z < eZ; z++, tY += pL) {
            for (x = bX, tX = pX; x < eX; x++, tX += pW) {

                worth = 0;
                shadingSum = 0;
                sumRf = sumGf = sumBf = 0;
                layers = 1;
                cavefloor = floorData.getHeightMapValue(x, z);//TODO test this

                //See-through-multi-level-height-light-shading is the new black -- @mithrilmania
                while (cavefloor > 0) {
                    caveceil = chunk.getCaveYUnderAt(x, z, cavefloor - 1);


                    cavefloor = chunk.getHighestBlockYUnderAt(x, z, caveceil - 1);
                    cavefloorW = (x == 0) ? (chunkW != null ? chunkW.getHighestBlockYUnderAt(dimension.chunkW - 1, z, caveceil - 1) : cavefloor)//chunk edge
                            : chunk.getHighestBlockYUnderAt(x - 1, z, caveceil - 1);//within chunk
                    cavefloorN = (z == 0) ? (chunkN != null ? chunkN.getHighestBlockYUnderAt(x, dimension.chunkL - 1, caveceil - 1) : cavefloor)//chunk edge
                            : chunk.getHighestBlockYUnderAt(x, z - 1, caveceil - 1);//within chunk

                    //height shading (based on slopes in terrain; height diff)
                    heightShading = SatelliteRenderer.getHeightShading(cavefloor, cavefloorW, cavefloorN);

                    y = cavefloor + 1;
                    data = chunk.getTerrain((byte) (y / cVersion.subChunkHeight));

                    //light sources
                    lightValue = (data != null && data.loadTerrain())
                            ? data.getBlockLightValue(x, y % cVersion.subChunkHeight, z)
                            : 0;

                    //check if it is supported, default to full brightness to not lose details.
                    if(data.supportsBlockLightValues()) {
                        lightShading = (float) lightValue / 15f + 1;
                    } else {
                        lightShading = 2f;
                    }


                    sliceShading = 0.5f + (((float) (caveceil - cavefloor)) / dimension.chunkH);

                    //mix shading
                    shading = heightShading * lightShading * sliceShading;
                    shading += 0.5f;

                    shadingSum += shading;




                    a = 1f;

                    offset = caveceil % cVersion.subChunkHeight;
                    stop = 0;
                    subChunk = caveceil / cVersion.subChunkHeight;
                    stopSubChunk = caveceil / cVersion.subChunkHeight;


                    subChunkLoop: for(; subChunk >= stopSubChunk; subChunk--) {
                        if (subChunk == stopSubChunk) stop = cavefloor % cVersion.subChunkHeight;

                        data = chunk.getTerrain((byte) subChunk);
                        if (data == null || !data.loadTerrain()) {
                            //start at the top of the next chunk! (current offset might differ)
                            offset = cVersion.subChunkHeight - 1;
                            continue;
                        }

                        for (y = offset; y >= stop; y--) {

                            id = data.getBlockTypeId(x, y, z) & 0xff;

                            if (id == 0) continue;//skip air blocks

                            meta = data.getBlockData(x, y, z) & 0xff;
                            block = Block.getBlock(id, meta);

                            //try the default meta value: 0
                            if (block == null) block = Block.getBlock(id, 0);

                            if (block == null) {
                                Log.w("UNKNOWN block: id: " + id + " meta: " + meta);
                                continue;
                            }

                            // no need to process block if it is fully transparent
                            if (block.color.alpha == 0) continue;

                            rf = block.color.red / 255f;
                            gf = block.color.green / 255f;
                            bf = block.color.blue / 255f;
                            af = block.color.alpha / 255f;

                            // alpha blend and multiply
                            blendR = a * af * rf * shading;
                            blendG = a * af * gf * shading;
                            blendB = a * af * bf * shading;

                            sumRf += blendR;
                            sumGf += blendG;
                            sumBf += blendB;
                            a *= 1f - af;

                            // break when an opaque block is encountered
                            if (block.color.alpha == 0xff) break subChunkLoop;
                        }

                        //start at the top of the next chunk! (current offset might differ)
                        offset = cVersion.subChunkHeight - 1;
                    }



                    layers++;
                }


                avgShading = shadingSum / layers;
                // apply the shading
                r = (int) (avgShading * sumRf / layers * 255f);
                g = (int) (avgShading * sumGf / layers * 255f);
                b = (int) (avgShading * sumBf / layers * 255f);


                r = r < 0 ? 0 : r > 255 ? 255 : r;
                g = g < 0 ? 0 : g > 255 ? 255 : g;
                b = b < 0 ? 0 : b > 255 ? 255 : b;


                subChunkLoop: for(subChunk = 0; subChunk < cVersion.subChunks; subChunk++) {
                    data = chunk.getTerrain((byte) subChunk);
                    if (data == null || data.loadTerrain()) break;

                    for (y = 0; y < cVersion.subChunkHeight; y++) {

                        //some x-ray for important stuff like portals
                        switch (data.getBlockTypeId(x, y, z)) {
                            case 52://monster spawner
                                r = g = b = 255;
                                break subChunkLoop;//max already? just stop
                            case 54://chest
                                if (worth < 90) {
                                    worth = 90;
                                    b = 170;
                                    r = 240;
                                    g = 40;
                                }
                                break;
                            case 115://nether wart
                                if (worth < 80) {
                                    worth = 80;
                                    r = b = 120;
                                    g = 170;
                                }
                                break;
                            case 90://nether portal
                                if (worth < 95) {
                                    worth = 95;
                                    r = 60;
                                    g = 0;
                                    b = 170;
                                }
                                break;
                        }
                    }
                }

                color = (r << 16) | (g << 8) | b | 0xff000000;

                for(i = 0; i < pL; i++){
                    for(j = 0; j < pW; j++){
                        bm.setPixel(tX + j, tY + i, color);
                    }
                }
            }
        }

        return bm;
    }

}

