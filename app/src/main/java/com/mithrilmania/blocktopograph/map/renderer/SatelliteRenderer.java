package com.mithrilmania.blocktopograph.map.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.block.Block;
import com.mithrilmania.blocktopograph.block.KnownBlockRepr;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.map.Dimension;


public class SatelliteRenderer implements MapRenderer {

    //calculate color of one column
    static int getColumnColour(@NonNull Chunk chunk, int x, int y, int z, int heightW, int heightN) throws Version.VersionException {
        float alphaRemain = 1f;
        float finalR = 0f;
        float finalG = 0f;
        float finalB = 0f;

        // extract colour components as normalized doubles, from ARGB format
        int grassColor = chunk.getGrassColor(x, z);
        float biomeR = (float) Color.red(grassColor) / 255f;
        float biomeG = (float) Color.green(grassColor) / 255f;
        float biomeB = (float) Color.blue(grassColor) / 255f;

        y--;
        for (; y >= 0 && alphaRemain >= .1f; y--) {

            Block block = chunk.getBlock(x, y, z, 0);

            KnownBlockRepr legacyBlock = block.getLegacyBlock();

            if (legacyBlock == KnownBlockRepr.B_0_0_AIR) continue;//skip air blocks

            int color = block.getColor();

            // no need to process block if it is fully transparent
            if (Color.alpha(color) == 0) continue;

            float blendA = Color.alpha(color) / 255f;

            // alpha blend and multiply
            float blendR = alphaRemain * blendA * (Color.red(color) / 255f);
            float blendG = alphaRemain * blendA * (Color.green(color) / 255f);
            float blendB = alphaRemain * blendA * (Color.blue(color) / 255f);

            //blend biome-colored blocks
            if (legacyBlock.hasBiomeShading) {
                blendR *= biomeR;
                blendG *= biomeG;
                blendB *= biomeB;
            }

            finalR += blendR;
            finalG += blendG;
            finalB += blendB;
            alphaRemain *= 1f - blendA;
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
        finalR = Math.min(Math.max(0f, finalR * shading), 1f);
        finalG = Math.min(Math.max(0f, finalG * shading), 1f);
        finalB = Math.min(Math.max(0f, finalB * shading), 1f);


        // now we have our final RGB values as floats, convert to a packed ARGB pixel.
        return 0xff000000 |
                ((((int) (finalR * 255f)) & 0xff) << 16) |
                ((((int) (finalG * 255f)) & 0xff) << 8) |
                (((int) (finalB * 255f)) & 0xff);
    }

    public void renderToBitmap(Chunk chunk, Canvas canvas, Dimension dimension, int chunkX, int chunkZ, int pX, int pY, int pW, int pL, Paint paint, WorldData worldData) throws Version.VersionException {

        Chunk dataW = worldData.getChunk(chunkX - 1, chunkZ, dimension);
        Chunk dataN = worldData.getChunk(chunkX, chunkZ - 1, dimension);

        boolean west = dataW != null && !dataW.isVoid(),
                north = dataN != null && !dataN.isVoid();

        for (int z = 0, tY = pY; z < 16; z++, tY += pL) {
            for (int x = 0, tX = pX; x < 16; x++, tX += pW) {

                int y = chunk.getHeightMapValue(x, z);
                if (y == 0) continue;

                int color = getColumnColour(chunk, x, y, z,
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