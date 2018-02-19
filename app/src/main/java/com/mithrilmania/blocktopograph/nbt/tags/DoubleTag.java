package com.mithrilmania.blocktopograph.nbt.tags;

import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;

public class DoubleTag extends Tag<Double> {

    private static final long serialVersionUID = 2230008080333021410L;

    public DoubleTag(String name, double value) {
        super(name, value);
    }

    @Override
    public NBTConstants.NBTType getType() {
        return NBTConstants.NBTType.DOUBLE;
    }

    @Override
    public DoubleTag getDeepCopy() {
        return new DoubleTag(name, value);
    }
}