package com.mithrilmania.blocktopograph.flat;

import androidx.annotation.Nullable;

import com.mithrilmania.blocktopograph.block.KnownBlockRepr;

import java.io.Serializable;

public final class Layer implements Serializable {

    private static long counter = 0;
    public KnownBlockRepr block;
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
        block = KnownBlockRepr.B_0_0_AIR;
        amount = 1;
        genUid();
    }

    Layer(KnownBlockRepr block, int amount) {
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
