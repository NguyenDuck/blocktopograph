package com.mithrilmania.blocktopograph.block;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.Tag;

import java.io.Serializable;
import java.util.ArrayList;

public class OldBlock implements Serializable {

    @NonNull
    private OldBlockType oldBlockType;

    @NonNull
    private KnownBlockRepr legacyBlock;

    private ListingBlock listingBlock;

    @NonNull
    private CompoundTag states;

    private int version;

    OldBlock(@NonNull OldBlockType oldBlockType, @NonNull CompoundTag states, int version) {
        this.oldBlockType = oldBlockType;
        this.states = states;
        this.version = version;
        KnownBlockRepr legacyBlock = BlockWithStatesToLegacyBlockMapper.getBestRepr(this);
        if (legacyBlock == null) {
            for (ListingBlock lb : ListingBlock.values()) {
                if (lb.getIdentifier().equals(oldBlockType.getName())) {
                    listingBlock = lb;
                    break;
                }
            }
            legacyBlock = KnownBlockRepr.guessBlockNew(oldBlockType.getName());
        }
        this.legacyBlock = legacyBlock;
    }

    OldBlock(@NonNull OldBlockType oldBlockType, @NonNull KnownBlockRepr legacyBlock, int version) {
        this.oldBlockType = oldBlockType;
        this.states = new CompoundTag("states", new ArrayList<>());
        this.version = version;
        this.legacyBlock = legacyBlock;
    }

    @NonNull
    public String getBlockType() {
        return oldBlockType.getName();
    }

    public int getVersion() {
        return version;
    }

    public boolean isOfSameType(OldBlock oldBlock) {
        return oldBlockType == oldBlock.oldBlockType;
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
        if (!(obj instanceof OldBlock)) return false;
        OldBlock another = (OldBlock) obj;
        // Ref compare.
        if (oldBlockType != another.oldBlockType) return false;
        return states.equals(another.states);
    }

}
