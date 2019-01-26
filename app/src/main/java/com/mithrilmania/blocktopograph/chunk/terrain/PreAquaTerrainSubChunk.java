package com.mithrilmania.blocktopograph.chunk.terrain;

import com.mithrilmania.blocktopograph.chunk.BedrockChunk;
import com.mithrilmania.blocktopograph.chunk.Chunk;

import java.nio.ByteBuffer;

public final class PreAquaTerrainSubChunk extends TerrainSubChunk {

    private static final int POS_BLOCK_IDS = 1;
    private static final int POS_META_DATA = 0x1001;
    private static final int POS_SKY_LIGHT = 0x1801;
    private static final int POS_BLOCK_LIGHT = 0x2001;
    private static final int TERRAIN_MAX_LENGTH = 0x2801;

    private ByteBuffer mData;

    PreAquaTerrainSubChunk(ByteBuffer raw) {
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

    @Override
    public int getBlockRuntimeId(int x, int y, int z) {
        if (mIsError) return 0;
        int offset = getOffset(x, y, z);
        int id = mData.get(POS_BLOCK_IDS + offset) & 0xff;
        int data = mData.get(POS_META_DATA + (offset >>> 1));
        data = (offset & 1) == 1 ? ((data >>> 4) & 0xf) : (data & 0xf);
        return (id << 8) | data;
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
}
