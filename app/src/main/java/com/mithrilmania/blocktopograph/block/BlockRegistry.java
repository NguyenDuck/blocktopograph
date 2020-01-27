package com.mithrilmania.blocktopograph.block;

import androidx.annotation.NonNull;

import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class BlockRegistry {

    private int limitedTypes;
    private Map<String, BlockType> blockTypes;

    public BlockRegistry() {
        blockTypes = new Hashtable<>(1024);
    }

    public BlockRegistry(int limitedTypes) {
        this();
        this.limitedTypes = limitedTypes;
    }

    @NonNull
    private BlockType getBlockType(String name) {
        BlockType ret = blockTypes.get(name);
        if (ret == null) {
            ret = new BlockType(name);
            if (limitedTypes > 0 && blockTypes.size() >= limitedTypes)
                throw new RuntimeException("Block types exceeds your set limit.");
            blockTypes.put(name, ret);
        }
        return ret;
    }

    @NonNull
    public Block createBlock(@NonNull String name, @NonNull CompoundTag states, int version) {
        return new Block(getBlockType(name), states, version);
    }

    @NonNull
    public Block createBlock(@NonNull String name) {
        return new Block(getBlockType(name), new CompoundTag("states", new ArrayList<>()), 3841);
    }

    @NonNull
    public Block createBlock(@NonNull KnownBlockRepr legacyBlock) {
        return new Block(getBlockType(legacyBlock.identifier), legacyBlock, 1);
    }

}
