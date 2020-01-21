package com.mithrilmania.blocktopograph.map.renderer;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.block.Block;
import com.mithrilmania.blocktopograph.block.KnownBlockRepr;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.map.Dimension;


public class NetherRenderer implements MapRenderer {

    public void renderToBitmap(Chunk chunk, Canvas canvas, Dimension dimension, int chunkX, int chunkZ, int pX, int pY, int pW, int pL, Paint paint, WorldData worldData) throws Version.VersionException {

        Chunk chunkW = worldData.getChunk(chunkX - 1, chunkZ, dimension);
        Chunk chunkN = worldData.getChunk(chunkX, chunkZ - 1, dimension);

        //Do you have to list all variables here in a 80s manner
        // regardless of many are only used within nested loop...

        float shading, shadingSum, a, blendR, blendG, blendB, sumRf, sumGf, sumBf;
        int layers;
        int caveceil, cavefloor, cavefloorW, cavefloorN;
        int worth;
        int y;
        float heightShading, lightShading, sliceShading, avgShading;

        for (int z = 0, tY = pY; z < 16; z++, tY += pL) {
            for (int x = 0, tX = pX; x < 16; x++, tX += pW) {

                worth = 0;
                shadingSum = 0;
                sumRf = sumGf = sumBf = 0;
                layers = 1;
                cavefloor = chunk.getHeightMapValue(x, z);//TODO test this

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

                    //check if it is supported, default to full brightness to not lose details.
                    if (chunk.supportsBlockLightValues()) {
                        lightShading = (float) chunk.getBlockLightValue(x, y, z) / 15f + 1;
                    } else {
                        lightShading = 2f;
                    }


                    sliceShading = 0.5f + (((float) (caveceil - cavefloor)) / dimension.chunkH);

                    //mix shading
                    shading = heightShading * lightShading * sliceShading;
                    shading += 0.5f;

                    shadingSum += shading;


                    a = 1f;

                    for (y = caveceil; y >= cavefloor; y--) {

                        Block block = chunk.getBlock(x, y, z, 0);

                        if (block.getLegacyBlock() == KnownBlockRepr.B_0_0_AIR)
                            continue;//skip air blocks

                        //try the default meta value: 0
                        //if (block == null) block = KnownBlockRepr.getBlock(id, 0);

                        int color = block.getColor();
                        // no need to process block if it is fully transparent
                        if (Color.alpha(color) == 0) continue;

                        float rf = Color.red(color) / 255f;
                        float gf = Color.green(color) / 255f;
                        float bf = Color.blue(color) / 255f;
                        float af = Color.alpha(color) / 255f;

                        // alpha blend and multiply
                        blendR = a * af * rf * shading;
                        blendG = a * af * gf * shading;
                        blendB = a * af * bf * shading;

                        sumRf += blendR;
                        sumGf += blendG;
                        sumBf += blendB;
                        a *= 1f - af;

                        // break when an opaque block is encountered
                        if (Color.alpha(color) == 0xff) break;
                    }

                    //start at the top of the next chunk! (current offset might differ)
                    //offset = 15;//cVersion.subChunkHeight - 1;
                    //}


                    layers++;
                }


                avgShading = shadingSum / layers;
                // apply the shading
                int r = (int) (avgShading * sumRf / layers * 255f);
                int g = (int) (avgShading * sumGf / layers * 255f);
                int b = (int) (avgShading * sumBf / layers * 255f);


                r = r < 0 ? 0 : r > 255 ? 255 : r;
                g = g < 0 ? 0 : g > 255 ? 255 : g;
                b = b < 0 ? 0 : b > 255 ? 255 : b;

                for (y = 0; y < chunk.getHeightLimit(); y++) {

                    //some x-ray for important stuff like portals
                    switch (chunk.getBlock(x, y, z, 0).getLegacyBlock()) {
                        case B_52_0_MOB_SPAWNER://monster spawner
                            r = g = b = 255;
                            break;
                        case B_54_0_CHEST://chest
                            if (worth < 90) {
                                worth = 90;
                                b = 170;
                                r = 240;
                                g = 40;
                            }
                            break;
                        case B_115_0_NETHER_WART://nether wart
                            if (worth < 80) {
                                worth = 80;
                                r = b = 120;
                                g = 170;
                            }
                            break;
                        case B_90_0_PORTAL://nether portal
                            if (worth < 95) {
                                worth = 95;
                                r = 60;
                                g = 0;
                                b = 170;
                            }
                            break;
                    }
                }

                paint.setColor((r << 16) | (g << 8) | b | 0xff000000);
                canvas.drawRect(new Rect(tX, tY, tX + pW, tY + pL), paint);
            }
        }
    }

}

