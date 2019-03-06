package com.mithrilmania.blocktopograph.map.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.map.Dimension;


public class ChessPatternRenderer implements MapRenderer {

    public final int darkShade, lightShade;// int DARK_SHADE = 0xFF2B2B2B, LIGHT_SHADE = 0xFF585858;

    ChessPatternRenderer(int darkShade, int lightShade) {
        this.darkShade = darkShade;
        this.lightShade = lightShade;
    }

    public void renderToBitmap(Chunk chunk, Canvas canvas, Dimension dimension, int chunkX, int chunkZ, int pX, int pY, int pW, int pL, Paint paint, WorldData worldData) throws Version.VersionException {

        int x, z, tX, tY;
        int color;

        for (z = 0, tY = pY; z < 16; z++, tY += pL) {
            for (x = 0, tX = pX; x < 16; x++, tX += pW) {
                color = ((x + z) & 1) == 1 ? darkShade : lightShade;
                paint.setColor(color);
                canvas.drawRect(new Rect(tX, tY, tX + pW, tY + pL), paint);
                //This would get hardware acceleration.
            }
        }
    }


}
