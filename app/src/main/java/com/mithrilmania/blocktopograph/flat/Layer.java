package com.mithrilmania.blocktopograph.flat;

import com.mithrilmania.blocktopograph.map.KnownBlock;

import java.io.Serializable;

import androidx.annotation.Nullable;

public final class Layer implements Serializable {

    private static long counter = 0;
    public KnownBlock block;
    public int amount;
    public long uid;

    private synchronized void genUid() {
        // What the hell are you doing?
        // Well calm down my friend....
        // Nothing serious, right?
        // ...
        uid = counter;//System.currentTimeMillis();
        counter++;
    }

    public Layer() {
        block = KnownBlock.B_0_0_AIR;
        amount = 1;
        genUid();
    }

    Layer(KnownBlock block, int amount) {
        this.block = block;
        this.amount = amount;
        genUid();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Layer)) return false;
        Layer another = (Layer) obj;
        if (amount != another.amount) return false;
        return block == another.block;
    }
}
