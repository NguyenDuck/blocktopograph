package com.mithrilmania.blocktopograph.map.renderer;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.block.BlockTemplates;
import com.mithrilmania.blocktopograph.block.OldBlock;
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

                for (int y = chunk.getHeightMapValue(x, z); y >= 0; y--) {

                    var blockTemplate = chunk.getBlockTemplate(x, y, z, 0);

                    //wooden plank
                    //stone bricks
                    //moss cobblestone
                    if (BlockTemplates.getAirTemplate().equals(blockTemplate)) {
                        //count the number of times it goes from solid to air
                        if (solid) layers++;
                        //count the air blocks underground,
                        // but avoid trees by skipping the first layer
                        if (intoSurface) cavyness++;
                    } else {
                        var blockName = blockTemplate.getBlock().getName();
                        if ("minecraft:rail".equals(blockName)) {//rail
                            if (b < 150) {
                                b = 150;
                                r = g = 50;
                            }
                        } else if ("minecraft:planks".equals(blockName)) {
                            if (b < 100) {
                                b = 100;
                                r = g = 100;
                            }
                        } else if ("minecraft:mob_spawner".equals(blockName)) {//monster spawner
                            r = g = b = 255;
                            break;
                        } else if ("minecraft:chest".equals(blockName)) {//chest
                            if (b < 170) {
                                b = 170;
                                r = 240;
                                g = 40;
                            }
                        } else if ("minecraft:stonebrick".equals(blockName)) {
                            if (b < 145) {
                                b = 145;
                                r = g = 120;
                            }
                        } else if ("minecraft:mossy_cobblestone".equals(blockName) || "minecraft:cobblestone".equals(blockName)) {//cobblestone
                            if (b < 140) {
                                b = 140;
                                r = g = 100;
                            }
                        }
                    }
                    r += chunk.getBlockLightValue(x, y, z);
                    solid = Color.alpha(blockTemplate.getColor()) == 0xff;
                    intoSurface |= solid && (y < 60 || layers > 0);
                }

                if (g == 0 && layers > 0) {
                    g = (r + 2) * cavyness;
                    r *= 32 * layers;
                    b = 16 * cavyness * (layers - 1);
                } else r *= r;


                r = r < 0 ? 0 : Math.min(r, 255);
                g = g < 0 ? 0 : Math.min(g, 255);
                //b = b < 0 ? 0 : b > 255 ? 255 : b;


                int color = (r << 16) | (g << 8) | b | 0xff000000;

                paint.setColor(color);
                canvas.drawRect(new Rect(tX, tY, tX + pW, tY + pL), paint);

            }
        }
    }

}

