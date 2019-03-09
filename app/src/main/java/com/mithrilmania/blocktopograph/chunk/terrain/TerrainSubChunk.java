package com.mithrilmania.blocktopograph.chunk.terrain;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.map.Dimension;

import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.nio.ByteBuffer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class TerrainSubChunk {

    boolean mHasSkyLight;
    boolean mHasBlockLight;
    boolean mIsError;

    @Nullable
    public static TerrainSubChunk create(@NonNull byte[] rawData) {
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
                subChunk = new PreV1d2d13TerrainSubChunk(byteBuffer);
                break;
            case 1:
            case 8:
                subChunk = new V1d2d13TerrainSubChunk(byteBuffer);
                break;
            default:
                subChunk = null;
        }
        return subChunk;
    }

    abstract public int getBlockRuntimeId(int x, int y, int z, int layer);

    abstract public void setBlockRuntimeId(int x, int y, int z, int layer, int runtimeId);

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

    abstract public void save(WorldData worldData, int chunkX, int chunkZ, Dimension dimension, int which) throws WorldData.WorldDBException, IOException;

}
