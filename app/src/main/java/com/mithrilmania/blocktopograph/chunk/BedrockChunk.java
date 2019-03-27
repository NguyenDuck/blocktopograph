package com.mithrilmania.blocktopograph.chunk;

import android.graphics.Color;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.terrain.TerrainSubChunk;
import com.mithrilmania.blocktopograph.map.Biome;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.util.Noise;

import java.io.IOException;
import java.nio.ByteBuffer;

import androidx.annotation.Nullable;

public final class BedrockChunk extends Chunk {

    private static final int POS_HEIGHTMAP = 0;
    private static final int POS_BIOME_DATA = 0x200;
    public static final int DATA2D_LENGTH = 0x300;

    private boolean mHasBlockLight;
    private final boolean[] mDirtyList;
    private final boolean[] mVoidList;
    private final boolean[] mErrorList;
    private boolean mIs2dDirty;
    private final TerrainSubChunk[] mTerrainSubChunks;
    private volatile ByteBuffer data2D;

    BedrockChunk(WorldData worldData, Version version, int chunkX, int chunkZ, Dimension dimension) {
        super(worldData, version, chunkX, chunkZ, dimension);
        mVoidList = new boolean[16];
        mErrorList = new boolean[16];
        mDirtyList = new boolean[16];
        mTerrainSubChunks = new TerrainSubChunk[16];
        load2dData();
        mHasBlockLight = true;
        mIs2dDirty = false;
    }

    private void load2dData() {
        if (data2D == null) {
            try {
                byte[] rawData = mWorldData.get().getChunkData(mChunkX, mChunkZ, ChunkTag.DATA_2D, mDimension, (byte) 0, false);
                if (rawData == null) {
                    mIsError = true;
                    mIsVoid = true;
                    return;
                }
                this.data2D = ByteBuffer.wrap(rawData);
            } catch (Exception e) {
                mIsError = true;
                mIsVoid = true;
            }
        }
    }

    @Nullable
    private TerrainSubChunk getSubChunk(int which) {
        if (mIsError || mVoidList[which]) return null;
        TerrainSubChunk ret = mTerrainSubChunks[which];
        if (ret == null) {
            byte[] raw;
            try {
                raw = mWorldData.get().getChunkData(mChunkX, mChunkZ,
                        ChunkTag.TERRAIN, mDimension, (byte) which, true);
                if (raw == null) {
                    mVoidList[which] = true;
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                mErrorList[which] = true;
                mVoidList[which] = true;
                return null;
            }
            ret = TerrainSubChunk.create(raw);
            if (ret == null || ret.isError()) {
                mVoidList[which] = true;
                mErrorList[which] = true;
                ret = null;
            } else if (!ret.hasBlockLight()) mHasBlockLight = false;
            mTerrainSubChunks[which] = ret;
        }
        return ret;
    }

    private int get2dOffset(int x, int z) {
        return (z << 4) | x;
    }

    @Override
    public boolean supportsBlockLightValues() {
        return mHasBlockLight;
    }

    @Override
    public boolean supportsHeightMap() {
        return true;
    }

    @Override
    public int getHeightLimit() {
        return 256;
    }

    @Override
    public int getHeightMapValue(int x, int z) {
        short h = data2D.getShort(POS_HEIGHTMAP + (get2dOffset(x, z) << 1));
        return ((h & 0xff) << 8) | ((h >> 8) & 0xff);
    }

    private void setHeightMapValue(int x, int z, short height) {
        data2D.putShort(POS_HEIGHTMAP + (get2dOffset(x, z) << 1), Short.reverseBytes(height));
    }

    @Override
    public int getBiome(int x, int z) {
        return data2D.get(POS_BIOME_DATA + get2dOffset(x, z));
    }

    private int getNoise(int base, int x, int z) {
        // noise values are between -1 and 1
        // 0.0001 is added to the coordinates because integer values result in 0
        double oct1 = Noise.noise(
                ((double) (mChunkX * 16 + x) / 100.0) + 0.0001,
                ((double) (mChunkZ * 16 + z) / 100.0) + 0.0001);
        double oct2 = Noise.noise(
                ((double) (mChunkX * 16 + x) / 20.0) + 0.0001,
                ((double) (mChunkZ * 16 + z) / 20.0) + 0.0001);
        double oct3 = Noise.noise(
                ((double) (mChunkX * 16 + x) / 3.0) + 0.0001,
                ((double) (mChunkZ * 16 + z) / 3.0) + 0.0001);
        return (int) (base + 60 + (40 * oct1) + (14 * oct2) + (6 * oct3));
    }

    @Override
    public int getGrassColor(int x, int z) {
        Biome biome = Biome.getBiome(getBiome(x, z) & 0xff);
        int r = getNoise(30 + (biome.color.red / 5), x, z);
        int g = getNoise(120 + (biome.color.green / 5), x, z);
        int b = getNoise(30 + (biome.color.blue / 5), x, z);
        return Color.rgb(r, g, b);
    }

    @Override
    public int getBlockRuntimeId(int x, int y, int z) {
        return getBlockRuntimeId(x, y, z, 0);
    }

    @Override
    public int getBlockRuntimeId(int x, int y, int z, int layer) {
        if (x >= 16 || y >= 256 || z >= 16 || x < 0 || y < 0 || z < 0 || mIsVoid)
            return 0;
        TerrainSubChunk subChunk = getSubChunk(y >> 4);
        if (subChunk == null) return 0;
        return subChunk.getBlockRuntimeId(x, y & 0xf, z, layer);
    }

    @Override
    public void setBlockRuntimeId(int x, int y, int z, int layer, int runtimeId) {
        if (x >= 16 || y >= 256 || z >= 16 || x < 0 || y < 0 || z < 0 || mIsVoid)
            return;
        int which = y >> 4;
        TerrainSubChunk subChunk = getSubChunk(which);
        if (subChunk == null) return;
        subChunk.setBlockRuntimeId(x, y & 0xf, z, layer, runtimeId);
        mDirtyList[which] = true;

        // Height increased.
        if (runtimeId != 0 && getHeightMapValue(x, z) < y) {
            mIs2dDirty = true;
            setHeightMapValue(x, z, (short) (y + 1));
            // Roof removed.
        } else if (runtimeId == 0 && getHeightMapValue(x, z) == y) {
            mIs2dDirty = true;
            int height = 0;
            for (int h = y - 1; h >= 0; h--) {
                if (getBlockRuntimeId(x, h, z) != 0) {
                    height = h + 1;
                    break;
                }
            }
            setHeightMapValue(x, z, (short) height);
        }
    }

    @Override
    public int getBlockLightValue(int x, int y, int z) {
        if (!mHasBlockLight || x >= 16 || y >= 256 || z >= 16 || x < 0 || y < 0 || z < 0 || mIsVoid)
            return 0;
        TerrainSubChunk subChunk = getSubChunk(y >> 4);
        if (subChunk == null) return 0;
        return subChunk.getBlockLightValue(x, y & 0xf, z);
    }

    @Override
    public int getSkyLightValue(int x, int y, int z) {
        if (x >= 16 || y >= 256 || z >= 16 || x < 0 || y < 0 || z < 0 || mIsVoid)
            return 0;
        TerrainSubChunk subChunk = getSubChunk(y >> 4);
        if (subChunk == null) return 0;
        return subChunk.getSkyLightValue(x, y & 0xf, z);
    }

    @Override
    public int getHighestBlockYUnderAt(int x, int z, int y) {
        if (x >= 16 || y >= 256 || z >= 16 || x < 0 || y < 0 || z < 0 || mIsVoid)
            return -1;
        TerrainSubChunk subChunk;
        for (int which = y >> 4; which >= 0; which--) {
            subChunk = getSubChunk(which);
            if (subChunk == null) continue;
            for (int innerY = (which == (y >> 4)) ? y & 0xf : 15; innerY >= 0; innerY--) {
                if (subChunk.getBlockRuntimeId(x, innerY, z, 0) != 0) return (which << 4) | innerY;
            }
        }
        return -1;
    }

    @Override
    public int getCaveYUnderAt(int x, int z, int y) {
        if (x >= 16 || y >= 256 || z >= 16 || x < 0 || y < 0 || z < 0 || mIsVoid)
            return -1;
        TerrainSubChunk subChunk;
        for (int which = y >> 4; which >= 0; which--) {
            subChunk = getSubChunk(which);
            if (subChunk == null) continue;
            for (int innerY = (which == (y >> 4)) ? y & 0xf : 15; innerY >= 0; innerY--) {
                if (subChunk.getBlockRuntimeId(x, innerY, z, 0) == 0) return (which << 4) | innerY;
            }
        }
        return -1;
    }

    @Override
    public void save() throws WorldData.WorldDBException, IOException {

        if (mIsError || mIsVoid) return;

        WorldData worldData = mWorldData.get();
        if (worldData == null)
            throw new RuntimeException("World data is null.");

        // Save biome and hightmap.
        if (mIs2dDirty)
            worldData.writeChunkData(
                    mChunkX, mChunkZ, ChunkTag.DATA_2D, mDimension, (byte) 0, false, data2D.array());

        // Save subChunks.
        for (int i = 0, mTerrainSubChunksLength = mTerrainSubChunks.length; i < mTerrainSubChunksLength; i++) {
            TerrainSubChunk subChunk = mTerrainSubChunks[i];
            if (subChunk == null || mVoidList[i] || !mDirtyList[i]) continue;
            //Log.d(this,"Saving "+i);
            subChunk.save(worldData, mChunkX, mChunkZ, mDimension, i);
        }
    }
}
