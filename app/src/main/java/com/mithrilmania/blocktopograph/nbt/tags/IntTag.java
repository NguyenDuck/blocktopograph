package com.mithrilmania.blocktopograph.nbt.tags;

import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;

public class IntTag extends Tag<Integer> {

    private static final long serialVersionUID = -4390371124151508132L;

    public IntTag(String name, int value) {
        super(name, value);
    }

    @Override
    public NBTConstants.NBTType getType() {
        return NBTConstants.NBTType.INT;
    }

    @Override
    public IntTag getDeepCopy() {
        return new IntTag(name, value);
    }
}