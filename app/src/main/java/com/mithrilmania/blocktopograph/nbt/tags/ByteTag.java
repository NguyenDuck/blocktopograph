package com.mithrilmania.blocktopograph.nbt.tags;

import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;

public class ByteTag extends Tag<Byte> {

    private static final long serialVersionUID = -8072877139532366356L;

    public ByteTag(String name, byte value) {
        super(name, value);
    }

    @Override
    public NBTConstants.NBTType getType() {
        return NBTConstants.NBTType.BYTE;
    }

    @Override
    public ByteTag getDeepCopy() {
        return new ByteTag(name, value);
    }
}