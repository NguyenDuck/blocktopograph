package com.mithrilmania.blocktopograph.chunk;

import androidx.annotation.NonNull;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.block.Block;
import com.mithrilmania.blocktopograph.block.KnownBlockRepr;
import com.mithrilmania.blocktopograph.map.Dimension;

import java.io.IOException;
import java.lang.ref.WeakReference;

public abstract class Chunk {

    protected final WeakReference<WorldData> mWorldData;
    protected final Version mVersion;
    public final int mChunkX;
    public final int mChunkZ;
    public final Dimension mDimension;
    protected NBTChunkData mEntity;
    protected NBTChunkData mTileEntity;
    boolean mIsVoid;
    boolean mIsError;

    Chunk(WorldData worldData, Version version, int chunkX, int chunkZ, Dimension dimension) {
        mWorldData = new WeakReference<>(worldData);
        mVersion = version;
        mChunkX = chunkX;
        mChunkZ = chunkZ;
        mDimension = dimension;
        mIsVoid = false;
        mIsError = false;
        try {
            mEntity = version.createEntityChunkData(this);
            mTileEntity = version.createBlockEntityChunkData(this);
        } catch (Version.VersionException e) {
            Log.d(this, e);
        }
    }

    public static Chunk createEmpty(@NonNull WorldData worldData, int chunkX, int chunkZ, Dimension dimension,
                                    Version createOfVersion) {
        Chunk chunk;
        switch (createOfVersion) {
            case V1_2_PLUS:
                try {
                    worldData.writeChunkData(chunkX, chunkZ, ChunkTag.GENERATOR_STAGE, dimension, (byte) 0, false, new byte[]{2, 0, 0, 0});
                    worldData.writeChunkData(chunkX, chunkZ, ChunkTag.VERSION, dimension, (byte) 0, false, new byte[]{0xf});
                    chunk = new BedrockChunk(worldData, createOfVersion, chunkX, chunkZ, dimension, true);
                } catch (Exception e) {
                    Log.d(Chunk.class, e);
                    chunk = new VoidChunk(worldData, createOfVersion, chunkX, chunkZ, dimension);
                }
                break;
            default:
                chunk = new VoidChunk(worldData, createOfVersion, chunkX, chunkZ, dimension);
        }
        return chunk;
    }

    public static Chunk create(@NonNull WorldData worldData, int chunkX, int chunkZ, Dimension dimension,
                               boolean createIfMissing, Version createOfVersion) {
        Version version;
        try {
            byte[] data = worldData.getChunkData(chunkX, chunkZ, ChunkTag.VERSION, dimension, (byte) 0, false);
            if (data == null && createIfMissing)
                return createEmpty(worldData, chunkX, chunkZ, dimension, createOfVersion);
            version = Version.getVersion(data);
        } catch (WorldData.WorldDBLoadException | WorldData.WorldDBException e) {
            Log.d(Chunk.class, e);
            version = Version.ERROR;
        }
        Chunk chunk;
        switch (version) {
            case ERROR:
            case OLD_LIMITED:
                chunk = new VoidChunk(worldData, version, chunkX, chunkZ, dimension);
                chunk.mIsError = true;
                break;
            case v0_9:
                chunk = new PocketChunk(worldData, version, chunkX, chunkZ, dimension);
                break;
            case V1_0:
            case V1_1:
            case V1_2_PLUS:
                chunk = new BedrockChunk(worldData, version, chunkX, chunkZ, dimension, false);
                break;
            case NULL:
            default:
                chunk = new VoidChunk(worldData, version, chunkX, chunkZ, dimension);
        }
        return chunk;
    }

    public final WorldData getWorldData() {
        return mWorldData.get();
    }


    public final boolean isVoid() {
        return mIsVoid;
    }


    public final boolean isError() {
        return mIsError;
    }

    abstract public boolean supportsBlockLightValues();

    abstract public boolean supportsHeightMap();

    abstract public int getHeightLimit();

    abstract public int getHeightMapValue(int x, int z);

    abstract public int getBiome(int x, int z);

    abstract public void setBiome(int x, int z, int id);

    abstract public int getGrassColor(int x, int z);

    @NonNull
    abstract public Block getBlock(int x, int y, int z);

    @NonNull
    abstract public Block getBlock(int x, int y, int z, int layer);

    abstract public void setBlock(int x, int y, int z, int layer, @NonNull Block block);

    abstract public int getBlockLightValue(int x, int y, int z);

    abstract public int getSkyLightValue(int x, int y, int z);

    abstract public int getHighestBlockYUnderAt(int x, int z, int y);

    abstract public int getCaveYUnderAt(int x, int z, int y);

    abstract public void save() throws WorldData.WorldDBException, IOException;

    @NonNull
    protected Block getAir() {
        return mWorldData.get().mBlockRegistry.createBlock(KnownBlockRepr.B_0_0_AIR);
    }

    public void deleteThis() throws Exception {
        WorldData worldData = mWorldData.get();
        if (worldData == null) throw new RuntimeException("World data is null.");
        worldData.removeChunkData(mChunkX, mChunkZ, ChunkTag.VERSION, mDimension, (byte) 0, false);
        worldData.removeChunkData(mChunkX, mChunkZ, ChunkTag.DATA_2D, mDimension, (byte) 0, false);
        worldData.removeChunkData(mChunkX, mChunkZ, ChunkTag.DATA_2D_LEGACY, mDimension, (byte) 0, false);
        worldData.removeChunkData(mChunkX, mChunkZ, ChunkTag.PENDING_TICKS, mDimension, (byte) 0, false);
        worldData.removeChunkData(mChunkX, mChunkZ, ChunkTag.GENERATOR_STAGE, mDimension, (byte) 0, false);
        worldData.removeChunkData(mChunkX, mChunkZ, ChunkTag.BIOME_STATE, mDimension, (byte) 0, false);
        worldData.removeChunkData(mChunkX, mChunkZ, ChunkTag.ENTITY, mDimension, (byte) 0, false);
        worldData.removeChunkData(mChunkX, mChunkZ, ChunkTag.BLOCK_ENTITY, mDimension, (byte) 0, false);
        worldData.removeChunkData(mChunkX, mChunkZ, ChunkTag.BLOCK_EXTRA_DATA, mDimension, (byte) 0, false);
        worldData.removeChunkData(mChunkX, mChunkZ, ChunkTag.V0_9_LEGACY_TERRAIN, mDimension, (byte) 0, false);
        for (byte i = 0; i < 16; i++) {
            worldData.removeChunkData(mChunkX, mChunkZ, ChunkTag.TERRAIN, mDimension, i, true);
        }
        // Prevent saving.
        mIsError = true;
    }


    public final NBTChunkData getEntity() {
        return mEntity;
    }


    public final NBTChunkData getBlockEntity() {
        return mTileEntity;
    }
}
