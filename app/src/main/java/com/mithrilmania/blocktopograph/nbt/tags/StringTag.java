package com.mithrilmania.blocktopograph.nbt.tags;

import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;

public class StringTag extends Tag<String> {

    private static final long serialVersionUID = 9167318877259218937L;

    public StringTag(String name, String value) {
        super(name, value);
    }

    @Override
    public NBTConstants.NBTType getType() {
        return NBTConstants.NBTType.STRING;
    }


    @Override
    public StringTag getDeepCopy() {
        return new StringTag(name, value);
    }
}