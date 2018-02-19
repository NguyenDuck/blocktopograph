package com.mithrilmania.blocktopograph.nbt.tags;

import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;

public class ShortArrayTag extends Tag<short[]> {

    private static final long serialVersionUID = -7722759532672413660L;

    public ShortArrayTag(String name, short[] value) {
        super(name, value);
    }

    @Override
    public NBTConstants.NBTType getType() {
        return NBTConstants.NBTType.SHORT_ARRAY;
    }

    @Override
    public Tag<short[]> getDeepCopy() {
        return new ShortArrayTag(name, value.clone());
    }

}
