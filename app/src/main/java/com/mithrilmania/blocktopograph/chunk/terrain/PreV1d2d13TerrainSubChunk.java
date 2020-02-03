package com.mithrilmania.blocktopograph.chunk.terrain;

import androidx.annotation.NonNull;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.block.Block;
import com.mithrilmania.blocktopograph.block.BlockRegistry;
import com.mithrilmania.blocktopograph.block.KnownBlockRepr;
import com.mithrilmania.blocktopograph.map.Dimension;

import java.nio.ByteBuffer;

public final class PreV1d2d13TerrainSubChunk extends TerrainSubChunk {

    private static final int POS_BLOCK_IDS = 1;
    private static final int POS_META_DATA = 0x1001;
    private static final int POS_SKY_LIGHT = 0x1801;
    private static final int POS_BLOCK_LIGHT = 0x2001;
    private static final int TERRAIN_MAX_LENGTH = 0x2801;

    private ByteBuffer mData;

    PreV1d2d13TerrainSubChunk(@NonNull ByteBuffer raw, @NonNull BlockRegistry blockRegistry) {

        super(blockRegistry);

        int size = raw.capacity();
        if (size < POS_SKY_LIGHT || size > TERRAIN_MAX_LENGTH) {
            mIsError = true;
            return;
        }
        mIsError = false;
        mData = ByteBuffer.allocate(size);
        mData.put(raw);
        mHasSkyLight = size >= POS_BLOCK_LIGHT;
        mHasBlockLight = size == TERRAIN_MAX_LENGTH;
    }

    @NonNull
    @Override
    public Block getBlock(int x, int y, int z, int layer) {
        if (mIsError) return getAir();
        int offset = getOffset(x, y, z);
        int id = mData.get(POS_BLOCK_IDS + offset) & 0xff;
        int data = mData.get(POS_META_DATA + (offset >>> 1));
        data = (offset & 1) == 1 ? ((data >>> 4) & 0xf) : (data & 0xf);
        return wrapKnownBlock(KnownBlockRepr.getBestBlock(id, data));
    }

    @Override
    public void setBlock(int x, int y, int z, int layer, @NonNull Block block) {
        // TODO implement setBlock for pre v1.2.13 subChunk.
    }

    @Override
    public int getBlockLightValue(int x, int y, int z) {
        if (mIsError || !mHasBlockLight) return 0;
        int offset = getOffset(x, y, z);
        int dualData = mData.get(POS_BLOCK_LIGHT + (offset >>> 1));
        return (offset & 1) == 1 ? (dualData >>> 4) & 0xf : dualData & 0xf;
    }

    @Override
    public int getSkyLightValue(int x, int y, int z) {
        if (mIsError || !mHasSkyLight) return 0;
        int offset = getOffset(x, y, z);
        int dualData = mData.get(POS_SKY_LIGHT + (offset >>> 1));
        return (offset & 1) == 1 ? (dualData >>> 4) & 0xf : dualData & 0xf;
    }

    @Override
    public void save(WorldData worldData, int chunkX, int chunkZ, Dimension dimension, int which) {
        // TODO implement save for pre v1.2.13 subChunk.
    }
}
