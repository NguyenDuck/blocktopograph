package com.mithrilmania.blocktopograph.util;

import android.graphics.Color;

public class ColorUtil {
    public static int truncateArgb(int a, int r, int g, int b) {
        return Color.argb(ensureColorRange(a),
                ensureColorRange(r), ensureColorRange(g), ensureColorRange(b)
        );
    }

    public static int truncateRgb(int r, int g, int b) {
        return Color.rgb(
                ensureColorRange(r), ensureColorRange(g), ensureColorRange(b)
        );
    }

    private static int ensureRange(int val, int min, int max) {
        return Math.min(Math.max(val, min), max);
    }

    private static int ensureColorRange(int val) {
        return ensureRange(val, 0, 255);
    }
}
