package com.mithrilmania.blocktopograph.block;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.Tag;

import java.util.ArrayList;

public class Block {

    @NonNull
    private BlockType blockType;

    @Nullable
    private KnownBlockRepr legacyBlock;

    @NonNull
    private CompoundTag states;

    private int version;

    Block(@NonNull BlockType blockType, @NonNull CompoundTag states, int version) {
        this.blockType = blockType;
        this.states = states;
        this.version = version;
        legacyBlock = BlockWithStatesToLegacyBlockMapper.getBestRepr(this);
        if (legacyBlock == null) legacyBlock = KnownBlockRepr.guessBlockNew(blockType.getName());
    }

    Block(@NonNull BlockType blockType, @NonNull KnownBlockRepr legacyBlock, int version) {
        this.blockType = blockType;
        this.states = new CompoundTag("", new ArrayList<>());
        this.version = version;
        this.legacyBlock = legacyBlock;
    }

    @NonNull
    public String getMinecraftBlockTypeNoPrefix() {
        String name = blockType.getName();
        int index = name.indexOf(':');
        if (index != -1 && name.substring(0, index).equals("minecraft"))
            name = name.substring(index + 1);
        return name;
    }

    @NonNull
    public String getBlockType() {
        return blockType.getName();
    }

    public int getVersion() {
        return version;
    }

    public boolean isOfSameType(Block block) {
        return blockType == block.blockType;
    }

    public Tag getState(String key) {
        return states.getChildTagByKey(key);
    }

    @Nullable
    public KnownBlockRepr getLegacyBlock() {
        return legacyBlock;
    }

    @NonNull
    public CompoundTag getStates() {
        return states;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Block)) return false;
        Block another = (Block) obj;
        // Ref compare.
        if (blockType != another.blockType) return false;
        return states.equals(another.states);
    }

}
