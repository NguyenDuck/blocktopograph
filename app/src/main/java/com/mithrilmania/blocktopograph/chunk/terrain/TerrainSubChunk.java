package com.mithrilmania.blocktopograph.chunk.terrain;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.map.Block;
import com.mithrilmania.blocktopograph.map.Dimension;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class TerrainSubChunk {

    private final WeakReference<WorldData.BlockRegistry> mBlockRegistry;

    boolean mHasSkyLight;
    boolean mHasBlockLight;
    boolean mIsError;

    protected TerrainSubChunk(@NotNull WorldData.BlockRegistry blockRegistry) {
        mBlockRegistry = new WeakReference<>(blockRegistry);
    }

    @Nullable
    public static TerrainSubChunk create(@NonNull byte[] rawData, @NotNull WorldData.BlockRegistry blockRegistry) {
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

    @NotNull
    abstract public Block getBlock(int x, int y, int z, int layer);

    abstract public void setBlock(int x, int y, int z, int layer, @NotNull Block block);

    abstract public int getBlockLightValue(int x, int y, int z);

    abstract public int getSkyLightValue(int x, int y, int z);

    protected static final int getOffset(int x, int y, int z) {
        return (((x << 4) | z) << 4) | y;
    }

    @Contract(pure = true)
    public final boolean hasBlockLight() {
        return mHasBlockLight;
    }

    @Contract(pure = true)
    public final boolean isError() {
        return mIsError;
    }

    @Nullable
    protected WorldData.BlockRegistry getBlockRegistry() {
        WorldData.BlockRegistry blockRegistry = mBlockRegistry.get();
        if (blockRegistry == null) {
            mIsError = true;
        }
        return blockRegistry;
    }

    abstract public void save(WorldData worldData, int chunkX, int chunkZ, Dimension dimension, int which) throws WorldData.WorldDBException, IOException;

}
