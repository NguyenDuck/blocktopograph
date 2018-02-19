package com.mithrilmania.blocktopograph.util.math;

import com.mithrilmania.blocktopograph.map.Dimension;

public class DimensionVector3<T extends Number> extends Vector3<T> {

    private static final long serialVersionUID = -3309069719341319245L;

    /**
     * dimension
     */
    public Dimension dimension;

    /**
     * Creates a vector with the given components
     *
     * @param x The x-component
     * @param y The y-component
     * @param z The z-component
     */
    public DimensionVector3(T x, T y, T z, Dimension dimension) {
        super(x, y, z);
        this.dimension = dimension;
    }
}