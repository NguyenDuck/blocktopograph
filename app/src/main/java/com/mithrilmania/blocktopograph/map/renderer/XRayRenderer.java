package com.mithrilmania.blocktopograph.map.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.block.KnownBlockRepr;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.map.Dimension;


public class XRayRenderer implements MapRenderer {

    /*
    TODO make the X-ray viewable blocks configurable, without affecting performance too much...
     */

    public void renderToBitmap(Chunk chunk, Canvas canvas, Dimension dimension, int chunkX, int chunkZ, int pX, int pY, int pW, int pL, Paint paint, WorldData worldData) throws Version.VersionException {

        //render width in blocks
        int rW = 16;
        int size2D = rW * (16);
        int index2D;
        KnownBlockRepr[] bestBlock = new KnownBlockRepr[size2D];

        int[] minValue = new int[size2D];
        int bValue;
        KnownBlockRepr block;

        int average;

        for (int z = 0; z < 16; z++) {
            for (int x = 0; x < 16; x++) {

                for (int y = 0; y < chunk.getHeightLimit(); y++) {
                    block = chunk.getBlock(x, y, z, 0).getLegacyBlock();

                    index2D = (z * rW) + x;
                    if (block.id <= 1)
                        continue;
                    else if (block == KnownBlockRepr.B_56_0_DIAMOND_ORE) {
                        bestBlock[index2D] = block;
                        break;
                    } else if (block == KnownBlockRepr.B_129_0_EMERALD_ORE) bValue = 8;
                    else if (block == KnownBlockRepr.B_153_0_QUARTZ_ORE) bValue = 7;
                    else if (block == KnownBlockRepr.B_14_0_GOLD_ORE) bValue = 6;
                    else if (block == KnownBlockRepr.B_15_0_IRON_ORE) bValue = 5;
                    else if (block == KnownBlockRepr.B_73_0_REDSTONE_ORE) bValue = 4;
                    else if (block == KnownBlockRepr.B_21_0_LAPIS_ORE) bValue = 3;
                        //else if(block == KnownBlockRepr.COAL_ORE) bValue = 2;
                        //else if(b == KnownBlockRepr.LAVA || b == KnownBlockRepr.STATIONARY_LAVA) bValue = 1;
                    else bValue = 0;

                    if (bValue > minValue[index2D]) {
                        minValue[index2D] = bValue;
                        bestBlock[index2D] = block;
                    }
                }
            }
        }


//        if (y == 0) {
//            MapType.CHESS.renderer.renderToBitmap(chunk, canvas, dimension, chunkX, chunkZ, pX, pY, pW, pL, paint, version, chunkManager);
//            return;
//        }

        for (int z = 0, tY = pY; z < 16; z++, tY += pL) {
            for (int x = 0, tX = pX; x < 16; x++, tX += pW) {
                block = bestBlock[(z * rW) + x];
                int color;
                if (block == null) {
                    color = 0xff000000;
                } else {

                    color = block.color;

                    int r = Color.red(color);
                    int g = Color.green(color);
                    int b = Color.blue(color);
                    average = (r + g + b) / 3;

                    //make the color better recognizable
                    r += r > average ? (r - average) * (r - average) : -(r - average) * (r - average);
                    g += g > average ? (g - average) * (g - average) : -(g - average) * (g - average);
                    b += b > average ? (b - average) * (b - average) : -(b - average) * (b - average);

                    if (r > 0xff) r = 0xff;
                    else if (r < 0) r = 0;
                    if (g > 0xff) g = 0xff;
                    else if (g < 0) g = 0;
                    if (b > 0xff) b = 0xff;
                    else if (b < 0) b = 0;

                    color = (r << 16) | (g << 8) | (b) | 0xff000000;
                }
                paint.setColor(color);
                canvas.drawRect(new Rect(tX, tY, tX + pW, tY + pL), paint);


            }
        }
    }

}
