package com.mithrilmania.blocktopograph.block;

import androidx.annotation.NonNull;

import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class OldBlockRegistry {

    private int limitedTypes;
    private Map<String, OldBlockType> blockTypes;

    public OldBlockRegistry() {
        blockTypes = new Hashtable<>(1024);
    }

    public OldBlockRegistry(int limitedTypes) {
        this();
        this.limitedTypes = limitedTypes;
    }

    @NonNull
    private OldBlockType getBlockType(String name) {
        OldBlockType ret = blockTypes.get(name);
        if (ret == null) {
            ret = new OldBlockType(name);
            if (limitedTypes > 0 && blockTypes.size() >= limitedTypes)
                throw new RuntimeException("Block types exceeds your set limit.");
            blockTypes.put(name, ret);
        }
        return ret;
    }

    @NonNull
    public OldBlock createBlock(@NonNull String name, @NonNull CompoundTag states, int version) {
        return new OldBlock(getBlockType(name), states, version);
    }

    @NonNull
    public OldBlock createBlock(@NonNull String name) {
        return new OldBlock(getBlockType(name), new CompoundTag("states", new ArrayList<>()), 3841);
    }

    @NonNull
    public OldBlock createBlock(@NonNull KnownBlockRepr legacyBlock) {
        return new OldBlock(getBlockType(legacyBlock.identifier), legacyBlock, 1);
    }

}
