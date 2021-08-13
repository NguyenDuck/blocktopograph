package com.mithrilmania.blocktopograph.block;

import java.io.Serializable;

public class OldBlockType implements Serializable {

    private final String name;

    public OldBlockType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
