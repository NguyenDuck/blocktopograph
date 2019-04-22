package com.mithrilmania.blocktopograph.map;

import androidx.annotation.Nullable;

public class UnknownBlock implements Block {

    public final int runtimeId;

    public final String name;

    public final int damage;

    public UnknownBlock(int runtimeId, String name, int damage) {
        this.runtimeId = runtimeId;
        this.name = name;
        this.damage = damage;
    }

    @Override
    public int getRuntimeId() {
        return runtimeId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getVal() {
        return damage;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof UnknownBlock) {
            return runtimeId == ((UnknownBlock) obj).runtimeId;
        }
        return false;
    }
}
