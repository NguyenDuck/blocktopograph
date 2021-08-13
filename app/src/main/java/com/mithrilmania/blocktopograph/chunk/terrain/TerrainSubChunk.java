package com.mithrilmania.blocktopograph.chunk.terrain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.block.Block;
import com.mithrilmania.blocktopograph.block.BlockTemplate;
import com.mithrilmania.blocktopograph.block.OldBlock;
import com.mithrilmania.blocktopograph.map.Dimension;

import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class TerrainSubChunk {

//    private final WeakReference<OldBlockRegistry> mBlockRegistry;

    boolean mHasSkyLight;
    boolean mHasBlockLight;
    boolean mIsError;

    protected TerrainSubChunk() {
//        mBlockRegistry = new WeakReference<>(oldBlockRegistry);
    }

    @Nullable
    public static TerrainSubChunk create(@NonNull byte[] rawData) {
        TerrainSubChunk subChunk;
        ByteBuffer byteBuffer = ByteBuffer.wrap(rawData);
        switch (rawData[0]) {
//            case 0:
//            case 2:
//            case 3:
//            case 4:
//            case 5:
//            case 6:
//            case 7:
//                subChunk = new PreV1d2d13TerrainSubChunk(byteBuffer, oldBlockRegistry);
//                break;
            case 1:
            case 8:
                subChunk = new V1d2d13TerrainSubChunk(byteBuffer);
                break;
            default:
                subChunk = null;
        }
        return subChunk;
    }

    @Nullable
    public static TerrainSubChunk createEmpty(int preferredVersion) {
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
                subChunk = new V1d2d13TerrainSubChunk();
                break;
            default:
                subChunk = null;
        }
        return subChunk;
    }

//    @NonNull
//    protected OldBlock wrapKnownBlock(KnownBlockRepr knownBlock) {
//        // TODO: This would be not efficiency for old saves, add corresponding oldBlock to known blocks.
//        return mBlockRegistry.get().createBlock(knownBlock);
//    }

    @NonNull
    abstract public BlockTemplate getBlockTemplate(int x, int y, int z, int layer);

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

//    @Nullable
//    protected OldBlockRegistry getBlockRegistry() {
//        OldBlockRegistry oldBlockRegistry = mBlockRegistry.get();
//        if (oldBlockRegistry == null) {
//            mIsError = true;
//        }
//        return oldBlockRegistry;
//    }

    abstract public void save(WorldData worldData, int chunkX, int chunkZ, Dimension dimension, int which) throws WorldData.WorldDBException, IOException;

}
