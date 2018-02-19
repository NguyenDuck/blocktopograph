package com.mithrilmania.blocktopograph.util;

/**
 * Simple wrapper around texture coordinates.
 */
public class UV {

    public final int uX, uY, vX, vY;

    public UV(int uX, int uY, int vX, int vY){
        this.uX = uX;
        this.uY = uY;
        this.vX = vX;
        this.vY = vY;
    }

    public static UV ab(int uX, int uY, int vX, int vY){
        return new UV(uX, uY, vX, vY);
    }

}
