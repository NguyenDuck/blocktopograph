package com.mithrilmania.blocktopograph.chunk.terrain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.block.Block;
import com.mithrilmania.blocktopograph.block.BlockRegistry;
import com.mithrilmania.blocktopograph.block.KnownBlockRepr;
import com.mithrilmania.blocktopograph.map.Dimension;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

public abstract class TerrainSubChunk {

    private final WeakReference<BlockRegistry> mBlockRegistry;

    boolean mHasSkyLight;
    boolean mHasBlockLight;
    boolean mIsError;

    protected TerrainSubChunk(@NonNull BlockRegistry blockRegistry) {
        mBlockRegistry = new WeakReference<>(blockRegistry);
    }

    @Nullable
    public static TerrainSubChunk create(@NonNull byte[] rawData, @NonNull BlockRegistry blockRegistry) {
        TerrainSubChunk subChunk;
        ByteBuffer byteBuffer = ByteBuffer.wrap(rawData);
        switch (rawData[0]) {
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                subChunk = new PreV1d2d13TerrainSubChunk(byteBuffer, blockRegistry);
                break;
            case 1:
            case 8:
                subChunk = new V1d2d13TerrainSubChunk(byteBuffer, blockRegistry);
                break;
            default:
                subChunk = null;
        }
        return subChunk;
    }

    @Nullable
    public static TerrainSubChunk createEmpty(int preferredVersion, @NonNull BlockRegistry blockRegistry) {
        TerrainSubChunk subChunk;
        switch (preferredVersion) {
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                subChunk = null;
                break;
            case 1:
            case 8:
                subChunk = new V1d2d13TerrainSubChunk(blockRegistry);
                break;
            default:
                subChunk = null;
        }
        return subChunk;
    }

    @NonNull
    protected Block getAir() {
        return wrapKnownBlock(KnownBlockRepr.B_0_0_AIR);
    }

    @NonNull
    protected Block wrapKnownBlock(KnownBlockRepr knownBlock) {
        // TODO: This would be not efficiency for old saves, add corresponding block to known blocks.
        return mBlockRegistry.get().createBlock(knownBlock);
    }

    @NonNull
    abstract public Block getBlock(int x, int y, int z, int layer);

    abstract public void setBlock(int x, int y, int z, int layer, @NonNull Block block);

    abstract public int getBlockLightValue(int x, int y, int z);

    abstract public int getSkyLightValue(int x, int y, int z);

    protected static final int getOffset(int x, int y, int z) {
        return (((x << 4) | z) << 4) | y;
    }


    public final boolean hasBlockLight() {
        return mHasBlockLight;
    }


    public final boolean isError() {
        return mIsError;
    }

    @Nullable
    protected BlockRegistry getBlockRegistry() {
        BlockRegistry blockRegistry = mBlockRegistry.get();
        if (blockRegistry == null) {
            mIsError = true;
        }
        return blockRegistry;
    }

    abstract public void save(WorldData worldData, int chunkX, int chunkZ, Dimension dimension, int which) throws WorldData.WorldDBException, IOException;

}
