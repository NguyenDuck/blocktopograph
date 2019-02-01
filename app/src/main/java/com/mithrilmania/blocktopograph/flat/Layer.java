package com.mithrilmania.blocktopograph.flat;

import com.mithrilmania.blocktopograph.map.Block;

import java.io.Serializable;

public final class Layer implements Serializable {

    public Block block;
    public int amount;

    public Layer() {
        block = Block.B_0_0_AIR;
        amount = 1;
    }

    Layer(Block block, int amount) {
        this.block = block;
        this.amount = amount;
    }

    public Layer(int legacyId, int amount) {
        Block block = Block.getBlockWithLegacyId(legacyId);
        if (block == null) this.block = Block.B_0_0_AIR;
        else this.block = block;
        amount = 0;
    }
}
