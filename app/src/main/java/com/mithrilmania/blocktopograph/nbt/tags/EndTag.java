package com.mithrilmania.blocktopograph.nbt.tags;

import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;

public class EndTag extends Tag<Object> {

    private static final long serialVersionUID = 1654129404501308744L;

    public EndTag() {
        super("", null);
    }

    @Override
    public NBTConstants.NBTType getType() {
        return NBTConstants.NBTType.END;
    }

    @Override
    public EndTag getDeepCopy() {
        return new EndTag();
    }
}