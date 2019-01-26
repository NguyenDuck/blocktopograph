package com.mithrilmania.blocktopograph.chunk;

/**
 * Reference from Tommaso Checchi (/u/mojang_tommo), MCPE developer:
 * https://www.reddit.com/r/MCPE/comments/5cw2tm/level_format_changes_in_mcpe_0171_100/d9zv9s8/
 */
public enum ChunkTag {

    DATA_2D((byte) 0x2D),
    DATA_2D_LEGACY((byte) 0x2E),
    TERRAIN((byte) 0x2F),
    V0_9_LEGACY_TERRAIN((byte) 0x30),
    BLOCK_ENTITY((byte) 0x31),
    ENTITY((byte) 0x32),
    PENDING_TICKS((byte) 0x33),//TODO untested
    BLOCK_EXTRA_DATA((byte) 0x34),//TODO untested, 32768 bytes, used for top-snow.
    BIOME_STATE((byte) 0x35),//TODO untested
    GENERATOR_STAGE((byte) 0x36),
    VERSION((byte) 0x76);


    public final byte dataID;

    ChunkTag(byte dataID) {
        this.dataID = dataID;
    }

}
