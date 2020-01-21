package com.mithrilmania.blocktopograph.map.renderer;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.block.Block;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.map.Dimension;


public class CaveRenderer implements MapRenderer {

    public void renderToBitmap(Chunk chunk, Canvas canvas, Dimension dimension, int chunkX, int chunkZ, int pX, int pY, int pW, int pL, Paint paint, WorldData worldData) throws Version.VersionException {

        boolean solid, intoSurface;
        int cavyness, layers;

        for (int z = 0, tY = pY; z < 16; z++, tY += pL) {
            for (int x = 0, tX = pX; x < 16; x++, tX += pW) {


                solid = false;
                intoSurface = false;
                cavyness = 0;
                layers = 0;
                /*
                while (cavefloor > 0) {
                    caveceil = chunk.getCaveYUnderAt(x, z, cavefloor - 1);
                    cavefloor = chunk.getHighestBlockYUnderAt(x, z, caveceil - 1);

                    //do not accept first cave-layer (most likely trees) as caves
                    if (layers > 1) cavyness += caveceil - cavefloor;

                    layers++;
                }
                */


                int r = 0, g = 0, b = 0;

                subChunkLoop:
                for (int y = chunk.getHeightMapValue(x, z); y >= 0; y--) {

                    Block block = chunk.getBlock(x, y, z, 0);

                    switch (block.getLegacyBlock()) {
                        case B_0_0_AIR:
                            //count the number of times it goes from solid to air
                            if (solid) layers++;

                            //count the air blocks underground,
                            // but avoid trees by skipping the first layer
                            if (intoSurface) cavyness++;
                            break;
                        case B_66_0_RAIL://rail
                            if (b < 150) {
                                b = 150;
                                r = g = 50;
                            }
                            break;
                        case B_5_0_PLANKS_OAK://wooden plank
                        case B_5_1_PLANKS_SPRUCE:
                        case B_5_2_PLANKS_BIRCH:
                        case B_5_3_PLANKS_JUNGLE:
                        case B_5_4_PLANKS_ACACIA:
                        case B_5_5_PLANKS_BIG_OAK:
                            if (b < 100) {
                                b = 100;
                                r = g = 100;
                            }
                            break;
                        case B_52_0_MOB_SPAWNER://monster spawner
                            r = g = b = 255;
                            break subChunkLoop;
                        case B_54_0_CHEST://chest
                            if (b < 170) {
                                b = 170;
                                r = 240;
                                g = 40;
                            }
                            break;
                        case B_98_0_STONEBRICK_DEFAULT://stone bricks
                        case B_98_1_STONEBRICK_MOSSY:
                        case B_98_2_STONEBRICK_CRACKED:
                        case B_98_3_STONEBRICK_CHISELED:
                        case B_98_4_STONEBRICK_SMOOTH:
                            if (b < 145) {
                                b = 145;
                                r = g = 120;
                            }
                            break;
                        case B_48_0_MOSSY_COBBLESTONE://moss cobblestone
                        case B_4_0_COBBLESTONE://cobblestone
                            if (b < 140) {
                                b = 140;
                                r = g = 100;
                            }
                            break;
                    }
                    r += chunk.getBlockLightValue(x, y, z);
                    solid = Color.alpha(block.getColor()) == 0xff;
                    intoSurface |= solid && (y < 60 || layers > 0);
                }

                if (g == 0 && layers > 0) {
                    g = (r + 2) * cavyness;
                    r *= 32 * layers;
                    b = 16 * cavyness * (layers - 1);
                } else r *= r;


                r = r < 0 ? 0 : r > 255 ? 255 : r;
                g = g < 0 ? 0 : g > 255 ? 255 : g;
                //b = b < 0 ? 0 : b > 255 ? 255 : b;


                int color = (r << 16) | (g << 8) | b | 0xff000000;

                paint.setColor(color);
                canvas.drawRect(new Rect(tX, tY, tX + pW, tY + pL), paint);

            }
        }
    }

}

