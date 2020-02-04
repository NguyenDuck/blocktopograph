package com.mithrilmania.blocktopograph.chunk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.block.Block;
import com.mithrilmania.blocktopograph.block.KnownBlockRepr;
import com.mithrilmania.blocktopograph.chunk.terrain.TerrainSubChunk;
import com.mithrilmania.blocktopograph.chunk.terrain.V1d2d13TerrainSubChunk;
import com.mithrilmania.blocktopograph.map.Biome;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.util.ColorUtil;
import com.mithrilmania.blocktopograph.util.Noise;

import java.io.IOException;
import java.nio.ByteBuffer;

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

    BedrockChunk(WorldData worldData, Version version, int chunkX, int chunkZ, Dimension dimension,
                 boolean createIfMissing) {
        super(worldData, version, chunkX, chunkZ, dimension);
        mVoidList = new boolean[16];
        mErrorList = new boolean[16];
        mDirtyList = new boolean[16];
        mTerrainSubChunks = new TerrainSubChunk[16];
        load2dData(createIfMissing);
        mHasBlockLight = true;
        mIs2dDirty = false;
    }

    private void load2dData(boolean createIfMissing) {
        if (data2D == null) {
            try {
                byte[] rawData = mWorldData.get().getChunkData(
                        mChunkX, mChunkZ, ChunkTag.DATA_2D, mDimension, (byte) 0, false);
                if (rawData == null) {
                    if (createIfMissing) {
                        this.data2D = ByteBuffer.allocate(0x300);
                    } else {
                        mIsError = true;
                        mIsVoid = true;
                    }
                    return;
                }
                this.data2D = ByteBuffer.wrap(rawData);
            } catch (Exception e) {
                mIsError = true;
                mIsVoid = true;
            }
        }
    }

    public V1d2d13TerrainSubChunk tempGetSubChunk() {
        return (V1d2d13TerrainSubChunk) getSubChunk(0, false);
    }

    @Nullable
    private TerrainSubChunk getSubChunk(int which, boolean createIfMissing) {
        if (mIsError || mVoidList[which]) return null;
        TerrainSubChunk ret = mTerrainSubChunks[which];
        if (ret == null) {
            byte[] raw;
            WorldData worldData = mWorldData.get();
            try {
                raw = worldData.getChunkData(mChunkX, mChunkZ,
                        ChunkTag.TERRAIN, mDimension, (byte) which, true);
                if (raw == null && !createIfMissing) {
                    mVoidList[which] = true;
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                mErrorList[which] = true;
                mVoidList[which] = true;
                return null;
            }
            ret = raw == null ?
                    TerrainSubChunk.createEmpty(8, worldData.mBlockRegistry) :
                    TerrainSubChunk.create(raw, worldData.mBlockRegistry);
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
        if (mIsVoid) return 0;
        short h = data2D.getShort(POS_HEIGHTMAP + (get2dOffset(x, z) << 1));
        return ((h & 0xff) << 8) | ((h >> 8) & 0xff);
    }

    private void setHeightMapValue(int x, int z, short height) {
        if (mIsVoid) return;
        data2D.putShort(POS_HEIGHTMAP + (get2dOffset(x, z) << 1), Short.reverseBytes(height));
    }

    @Override
    public int getBiome(int x, int z) {
        if (mIsVoid) return 0;
        return data2D.get(POS_BIOME_DATA + get2dOffset(x, z));
    }

    @Override
    public void setBiome(int x, int z, int id) {
        if (mIsVoid) return;
        data2D.put(POS_BIOME_DATA + get2dOffset(x, z), (byte) id);
        mIs2dDirty = true;
    }

    private int getNoise(int x, int z) {
        // noise values are between -1 and 1
        // 0.0001 is added to the coordinates because integer values result in 0
        double xval = (mChunkX << 4) | x;
        double zval = (mChunkZ << 4) | z;
        double oct1 = Noise.noise(
                (xval / 100.0) % 256 + 0.0001,
                (zval / 100.0) % 256 + 0.0001);
        double oct2 = Noise.noise(
                (xval / 20.0) % 256 + 0.0001,
                (zval / 20.0) % 256 + 0.0001);
        double oct3 = Noise.noise(
                (xval / 3.0) % 256 + 0.0001,
                (zval / 3.0) % 256 + 0.0001);
        return (int) (60 + (40 * oct1) + (14 * oct2) + (6 * oct3));
    }

    @Override
    public int getGrassColor(int x, int z) {
        Biome biome = Biome.getBiome(getBiome(x, z) & 0xff);
        int noise = getNoise(x, z);
        int r = 30 + (biome.color.red / 5) + noise;
        int g = 110 + (biome.color.green / 5) + noise;
        int b = 30 + (biome.color.blue / 5) + noise;
        return ColorUtil.truncateRgb(r, g, b);
    }

    @NonNull
    @Override
    public Block getBlock(int x, int y, int z) {
        return getBlock(x, y, z, 0);
    }

    @NonNull
    @Override
    public Block getBlock(int x, int y, int z, int layer) {
        if (x >= 16 || y >= 256 || z >= 16 || x < 0 || y < 0 || z < 0 || mIsVoid)
            return getAir();
        TerrainSubChunk subChunk = getSubChunk(y >> 4, false);
        if (subChunk == null)
            return getAir();
        return subChunk.getBlock(x, y & 0xf, z, layer);
    }

    @Override
    public void setBlock(int x, int y, int z, int layer, @NonNull Block block) {
        if (x >= 16 || y >= 256 || z >= 16 || x < 0 || y < 0 || z < 0 || mIsVoid)
            return;
        int which = y >> 4;
        TerrainSubChunk subChunk = getSubChunk(which, true);
        if (subChunk == null) return;
        subChunk.setBlock(x, y & 0xf, z, layer, block);
        mDirtyList[which] = true;
        KnownBlockRepr repr = block.getLegacyBlock();

        // Height increased.
        if (repr != KnownBlockRepr.B_0_0_AIR && getHeightMapValue(x, z) < y) {
            mIs2dDirty = true;
            setHeightMapValue(x, z, (short) (y + 1));
            // Roof removed.
        } else if (repr == KnownBlockRepr.B_0_0_AIR && getHeightMapValue(x, z) == y) {
            mIs2dDirty = true;
            int height = 0;
            for (int h = y - 1; h >= 0; h--) {
                Block blockAtHeight = getBlock(x, h, z);
                KnownBlockRepr reprAtHeight = blockAtHeight.getLegacyBlock();
                if (reprAtHeight != KnownBlockRepr.B_0_0_AIR) {
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
        TerrainSubChunk subChunk = getSubChunk(y >> 4, false);
        if (subChunk == null) return 0;
        return subChunk.getBlockLightValue(x, y & 0xf, z);
    }

    @Override
    public int getSkyLightValue(int x, int y, int z) {
        if (x >= 16 || y >= 256 || z >= 16 || x < 0 || y < 0 || z < 0 || mIsVoid)
            return 0;
        TerrainSubChunk subChunk = getSubChunk(y >> 4, false);
        if (subChunk == null) return 0;
        return subChunk.getSkyLightValue(x, y & 0xf, z);
    }

    @Override
    public int getHighestBlockYUnderAt(int x, int z, int y) {
        if (x >= 16 || y >= 256 || z >= 16 || x < 0 || y < 0 || z < 0 || mIsVoid)
            return -1;
        TerrainSubChunk subChunk;
        for (int which = y >> 4; which >= 0; which--) {
            subChunk = getSubChunk(which, false);
            if (subChunk == null) continue;
            for (int innerY = (which == (y >> 4)) ? y & 0xf : 15; innerY >= 0; innerY--) {
                Block block = subChunk.getBlock(x, innerY, z, 0);
                KnownBlockRepr repr = block.getLegacyBlock();
                if (repr != KnownBlockRepr.B_0_0_AIR)
                    return (which << 4) | innerY;
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
            subChunk = getSubChunk(which, false);
            if (subChunk == null) continue;
            for (int innerY = (which == (y >> 4)) ? y & 0xf : 15; innerY >= 0; innerY--) {
                Block block = subChunk.getBlock(x, innerY, z, 0);
                if (block.getLegacyBlock() == KnownBlockRepr.B_0_0_AIR)
                    return (which << 4) | innerY;
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
