package com.mithrilmania.blocktopograph.map;


import androidx.annotation.StringRes;

import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.map.renderer.MapType;

import java.util.HashMap;
import java.util.Map;

public enum Dimension {

    OVERWORLD(0, "overworld", "Overworld", 16, 16, 128, 1, MapType.OVERWORLD_SATELLITE),
    NETHER(1, "nether", "Nether", 16, 16, 128, 1, MapType.NETHER),
    END(2, "end", "End", 16, 16, 128, 1, MapType.END_SATELLITE);//mcpe: SOON^TM /jk

    public final int id;
    public final int chunkW, chunkL, chunkH;
    public final int dimensionScale;
    public final String dataName, name;
    public final MapType defaultMapType;

    Dimension(int id, String dataName, String name, int chunkW, int chunkL, int chunkH, int dimensionScale, MapType defaultMapType) {
        this.id = id;
        this.dataName = dataName;
        this.name = name;
        this.chunkW = chunkW;
        this.chunkL = chunkL;
        this.chunkH = chunkH;
        this.dimensionScale = dimensionScale;
        this.defaultMapType = defaultMapType;
    }

    @StringRes

    public int getName() {
        switch (this) {
            case OVERWORLD:
                return R.string.overworld;
            case NETHER:
                return R.string.nether;
            case END:
                return R.string.the_end;
            default:
                return 0;
        }
    }

    private static Map<String, Dimension> dimensionMap = new HashMap<>();

    static {
        for (Dimension dimension : Dimension.values()) {
            dimensionMap.put(dimension.dataName, dimension);
        }
    }

    public static Dimension getDimension(String dataName) {
        if (dataName == null) return null;
        return dimensionMap.get(dataName.toLowerCase());
    }

    public static Dimension getDimension(int id) {
        for (Dimension dimension : values()) {
            if (dimension.id == id) return dimension;
        }
        return null;
    }

}
