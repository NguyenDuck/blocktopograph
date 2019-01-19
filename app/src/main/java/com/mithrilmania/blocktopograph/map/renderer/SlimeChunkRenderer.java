package com.mithrilmania.blocktopograph.map.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.mithrilmania.blocktopograph.chunk.ChunkManager;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.util.MTwister;


public class SlimeChunkRenderer implements MapRenderer {

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

        int x, z, i, j, tX, tY;

        MapType.OVERWORLD_SATELLITE.renderer.renderToBitmap(cm, bm, dimension, chunkX, chunkZ, pX, pY, pW, pL);

        boolean isSlimeChunk = isSlimeChunk(chunkX, chunkZ);
        int color, r, g, b, avg;
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint();

        //make slimeChunks much more green
        for (z = 0, tY = pY; z < 16; z++, tY += pL) {
            for (x = 0, tX = pX; x < 16; x++, tX += pW) {
                color = bm.getPixel(tX, tY);
                r = (color >> 16) & 0xff;
                g = (color >> 8) & 0xff;
                b = color & 0xff;
                avg = (r + g + b) / 3;
                if (isSlimeChunk) {
                    r = b = avg;
                    g = (g + 0xff) >> 1;
                } else {
                    r = g = b = avg;
                }
                color = (color & 0xFF000000) | (r << 16) | (g << 8) | b;

                paint.setColor(color);
                canvas.drawRect(new Rect(tX, tY, tX + pW, tY + pL), paint);

            }
        }

        return bm;
    }


    // See: https://gist.github.com/mithrilmania/00b85bf34a75fd8176342b1ad28bfccc
    public static boolean isSlimeChunk(int cX, int cZ) {
        //
        // MCPE slime-chunk checker
        // From Minecraft: Pocket Edition 0.15.0 (0.15.0.50_V870150050)
        // Reverse engineered by @mithrilmania and @jocopa3
        //
        // NOTE:
        // - The world-seed doesn't seem to be incorporated into the randomness, which is very odd.
        //   This means that every world has its slime-chunks in the exact same chunks!
        //   This is not officially confirmed yet.
        // - Reverse engineering this code cost a lot of time,
        //   please add CREDITS when you are copying this.
        //   Copy the following into your program source:
        //     MCPE slime-chunk checker; reverse engineered by @mithrilmania and @jocopa3
        //

        // chunkX/Z are the chunk-coordinates, used in the DB keys etc.
        // Unsigned int32, using 64 bit longs to work-around the sign issue.
        long chunkX_uint = cX & 0xffffffffL;
        long chunkZ_uint = cZ & 0xffffffffL;

        // Combine X and Z into a 32 bit int (again, long to work around sign issue)
        long seed = (chunkX_uint * 0x1f1f1f1fL) ^ chunkZ_uint;

        // The random function MCPE uses, not the same as MCPC!
        // This is a Mersenne Twister; MT19937 by Takuji Nishimura and Makoto Matsumoto.
        // Java adaption source: http://dybfin.wustl.edu/teaching/compufinj/MTwister.java
        MTwister random = new MTwister();
        random.init_genrand(seed);

        // The output of the random function, first operand of the asm umull instruction
        long n = random.genrand_int32();

        // The other operand, magic bit number that keeps characteristics
        // In binary: 1100 1100 1100 1100 1100 1100 1100 1101
        long m = 0xcccccccdL;

        // umull (unsigned long multiplication)
        // Stores the result of multiplying two int32 integers in two registers (lo and hi).
        // Java workaround: store the result in a 64 bit long, instead of two 32 bit registers.
        long product = n * m;

        // The umull instruction puts the result in a lo and a hi register, the lo one is not used.
        long hi = (product >> 32) & 0xffffffffL;

        // Make room for 3 bits, preparation for decrease of randomness by a factor 10.
        long hi_shift3 = (hi >> 0x3) & 0xffffffffL;

        // Multiply with 10 (3 bits)
        // ---> effect: the 3 bit randomness decrease expresses a 1 in a 10 chance.
        long res = (((hi_shift3 + (hi_shift3 * 0x4)) & 0xffffffffL) * 0x2) & 0xffffffffL;

        // Final check: is the input equal to 10 times less random, but comparable, output.
        // Every chunk has a 1 in 10 chance to be a slime-chunk.
        return n == res;
    }

}