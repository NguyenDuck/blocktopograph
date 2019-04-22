package com.mithrilmania.blocktopograph.map.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.map.KnownBlock;

import org.jetbrains.annotations.NotNull;


public class SatelliteRenderer implements MapRenderer {

    public void renderToBitmap(Chunk chunk, Canvas canvas, Dimension dimension, int chunkX, int chunkZ, int pX, int pY, int pW, int pL, Paint paint, WorldData worldData) throws Version.VersionException {

        Chunk dataW = worldData.getChunk(chunkX - 1, chunkZ, dimension);
        Chunk dataN = worldData.getChunk(chunkX, chunkZ - 1, dimension);

        boolean west = dataW != null && !dataW.isVoid(),
                north = dataN != null && !dataN.isVoid();

        int x, y, z, color, i, j, tX, tY;

        for (z = 0, tY = pY; z < 16; z++, tY += pL) {
            for (x = 0, tX = pX; x < 16; x++, tX += pW) {

                y = chunk.getHeightMapValue(x, z);
                if (y == 0) continue;

                color = getColumnColour(chunk, x, y, z,
                        (x == 0) ? (west ? dataW.getHeightMapValue(dimension.chunkW - 1, z) : y)//chunk edge
                                : chunk.getHeightMapValue(x - 1, z),//within chunk
                        (z == 0) ? (north ? dataN.getHeightMapValue(x, dimension.chunkL - 1) : y)//chunk edge
                                : chunk.getHeightMapValue(x, z - 1)//within chunk
                );
                paint.setColor(color);
                canvas.drawRect(new Rect(tX, tY, tX + pW, tY + pL), paint);


            }
        }

    }

    //calculate color of one column
    static int getColumnColour(@NotNull Chunk chunk, int x, int y, int z, int heightW, int heightN) throws Version.VersionException {
        float a = 1f;
        float r = 0f;
        float g = 0f;
        float b = 0f;

        // extract colour components as normalized doubles, from ARGB format
        int colint = chunk.getGrassColor(x, z);
        float biomeR = (float) Color.red(colint) / 255f;
        float biomeG = (float) Color.green(colint) / 255f;
        float biomeB = (float) Color.blue(colint) / 255f;

        float blendR, blendG, blendB;

        float blockA, blockR, blockG, blockB;


        KnownBlock block;

        y--;
        for (; y >= 0; y--) {

            block = chunk.getKnownBlock(x, y, z, 0);

            if (block == KnownBlock.B_0_0_AIR) continue;//skip air blocks

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
                break;
            }
        }

        //height shading (based on slopes in terrain; height diff)
        float heightShading = getHeightShading(y, heightW, heightN);

        //go back to "surface"
        y++;
        //light sources
        int lightValue = chunk.getBlockLightValue(x, y, z) & 0xff;
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