package com.mithrilmania.blocktopograph.block;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.Tag;

import java.io.Serializable;
import java.util.ArrayList;

public class Block implements Serializable {

    @NonNull
    private BlockType blockType;

    @NonNull
    private KnownBlockRepr legacyBlock;

    private ListingBlock listingBlock;

    @NonNull
    private CompoundTag states;

    private int version;

    Block(@NonNull BlockType blockType, @NonNull CompoundTag states, int version) {
        this.blockType = blockType;
        this.states = states;
        this.version = version;
        KnownBlockRepr legacyBlock = BlockWithStatesToLegacyBlockMapper.getBestRepr(this);
        if (legacyBlock == null) {
            for (ListingBlock lb : ListingBlock.values()) {
                if (lb.getIdentifier().equals(blockType.getName())) {
                    listingBlock = lb;
                    break;
                }
            }
            legacyBlock = KnownBlockRepr.guessBlockNew(blockType.getName());
        }
        this.legacyBlock = legacyBlock;
    }

    Block(@NonNull BlockType blockType, @NonNull KnownBlockRepr legacyBlock, int version) {
        this.blockType = blockType;
        this.states = new CompoundTag("states", new ArrayList<>());
        this.version = version;
        this.legacyBlock = legacyBlock;
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

    @NonNull
    public KnownBlockRepr getLegacyBlock() {
        return legacyBlock;
    }

    public int getColor() {
        if (listingBlock != null)
            return listingBlock.getColor();
        return legacyBlock.color;
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
