package com.mithrilmania.blocktopograph.block.blockproperty;

import com.mithrilmania.blocktopograph.nbt.tags.Tag;

public class EnumValuesRange extends ValuesRange {

    private final Object[] values;

    public EnumValuesRange(Object[] values) {
        this.values = values;
    }

    public Object[] getValues() {
        return values;
    }
}
