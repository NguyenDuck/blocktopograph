package com.mithrilmania.blocktopograph.nbt.tags;

import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;

public class ByteArrayTag extends Tag<byte[]> {

    private static final long serialVersionUID = 5667709255740878805L;

    public ByteArrayTag(String name, byte[] value) {
        super(name, value);
    }

    @Override
    public NBTConstants.NBTType getType() {
        return NBTConstants.NBTType.BYTE_ARRAY;
    }

    @Override
    public Tag<byte[]> getDeepCopy() {
        return new ByteArrayTag(name, value.clone());
    }

}