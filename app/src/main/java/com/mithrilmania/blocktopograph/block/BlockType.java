package com.mithrilmania.blocktopograph.block;

import java.io.Serializable;

public class BlockType implements Serializable {

    private String name;

    public BlockType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
