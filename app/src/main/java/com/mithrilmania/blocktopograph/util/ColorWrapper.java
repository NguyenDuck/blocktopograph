package com.mithrilmania.blocktopograph.util;


import androidx.annotation.NonNull;

public class ColorWrapper {

    private static final int BIT_MASK = 0xff;

    public final int alpha;

    public final int red;
    public final int green;
    public final int blue;

    public ColorWrapper(int alpha, int red, int green, int blue) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Creates a new ColorWrapper object from a red, green, and blue
     *
     * @param red   integer from 0-255
     * @param green integer from 0-255
     * @param blue  integer from 0-255
     * @return a new ColorWrapper object for the red, green, blue
     * @throws IllegalArgumentException if any value is strictly >255 or <0
     */
    public static ColorWrapper fromRGB(int red, int green, int blue) throws IllegalArgumentException {
        return new ColorWrapper(0xff, red, green, blue);
    }

    /**
     * Creates a new color object from an integer that contains the red, green, and blue bytes in the lowest order 24 bits.
     *
     * @param rgb the integer storing the red, green, and blue values
     * @return a new color object for specified values
     * @throws IllegalArgumentException if any data is in the highest order 8 bits
     */
    public static ColorWrapper fromRGB(int rgb) throws IllegalArgumentException {
        return fromRGB(rgb >> 16 & BIT_MASK, rgb >> 8 & BIT_MASK, rgb & BIT_MASK);
    }

    public static ColorWrapper fromARGB(int alpha, int red, int green, int blue) throws IllegalArgumentException {
        return new ColorWrapper(alpha, red, green, blue);
    }

    public static ColorWrapper fromARGB(int argb) throws IllegalArgumentException {
        return fromARGB(argb >> 24 & BIT_MASK, argb >> 16 & BIT_MASK, argb >> 8 & BIT_MASK, argb & BIT_MASK);
    }

    /**
     * @return An integer representation of this color, as 0xRRGGBB
     */
    public int asRGB() {
        return red << 16 | green << 8 | blue;
    }

    public int asARGB() {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ColorWrapper)) {
            return false;
        }
        final ColorWrapper that = (ColorWrapper) o;
        return this.alpha == that.alpha && this.red == that.red && this.green == that.green && this.blue == that.blue;
    }

    @Override
    public int hashCode() {
        return asRGB() ^ ColorWrapper.class.hashCode();
    }


    @NonNull
    @Override
    public String toString() {
        return "ColorWrapper:[argb0x"
                + Integer.toHexString(alpha)
                + Integer.toHexString(red)
                + Integer.toHexString(green)
                + Integer.toHexString(blue)
                + "]";
    }
}
