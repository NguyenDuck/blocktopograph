package com.mithrilmania.blocktopograph.chunk;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.block.Block;
import com.mithrilmania.blocktopograph.block.KnownBlockRepr;
import com.mithrilmania.blocktopograph.map.Dimension;

import java.nio.ByteBuffer;

public final class PocketChunk extends Chunk {


    private static final int POS_BLOCK_IDS = 0;
    private static final int POS_META_DATA = 0x8000;
    private static final int POS_SKY_LIGHT = 0xc000;
    private static final int POS_BLOCK_LIGHT = 0x10000;
    //Isn't this "dirty table"?
    //Seems Proto got it wrong...
    private static final int POS_HEIGHTMAP = 0x14000;
    private static final int POS_BIOME_DATA = 0x14100;
    private static final int LENGTH = 0x14500;

    private volatile ByteBuffer mData;

    PocketChunk(WorldData worldData, Version version, int chunkX, int chunkZ, Dimension dimension) {
        super(worldData, version, chunkX, chunkZ, dimension);
        tryLoad();
    }

    private void tryLoad() {
        if (mData == null) {
            try {
                byte[] rawData = mWorldData.get().getChunkData(mChunkX, mChunkZ, ChunkTag.V0_9_LEGACY_TERRAIN, mDimension, (byte) 0, false);
                if (rawData == null) {
                    mIsVoid = true;
                    return;
                }
                mData = ByteBuffer.allocate(rawData.length);
                mData.put(rawData, 0, rawData.length);
                mIsVoid = false;
            } catch (Exception e) {
                mIsError = true;
                mIsVoid = true;
            }
        }
    }

    public void createEmpty() {
        byte[] chunk = new byte[LENGTH];
        int x, y, z, i = 0;
        byte bedrock = (byte) 7;
        byte sandstone = (byte) 24;

        //generate super basic terrain (one layer of bedrock, 31 layers of sandstone)
        //Emmm but why
        for (x = 0; x < 16; x++) {
            for (z = 0; z < 16; z++) {
                for (y = 0; y < 128; y++, i++) {
                    chunk[i] = (y == 0 ? bedrock : (y < 32 ? sandstone : 0));
                }
            }
        }

        //fill meta-data with 0
        for (; i < POS_SKY_LIGHT; i++) {
            chunk[i] = 0;
        }

        //fill blocklight with 0xff
        for (; i < POS_BLOCK_LIGHT; i++) {
            chunk[i] = (byte) 0xff;
        }

        //fill block-light with 0xff
        for (; i < POS_HEIGHTMAP; i++) {
            chunk[i] = (byte) 0xff;
        }

        //fill heightmap
        for (; i < POS_BIOME_DATA; i++) {
            chunk[i] = 32;
        }

        //fill biome data
        for (; i < LENGTH; ) {
            chunk[i++] = 1;//biome: plains
            chunk[i++] = (byte) 42;//r
            chunk[i++] = (byte) 42;//g
            chunk[i++] = (byte) 42;//b
        }

        this.mData = ByteBuffer.wrap(chunk);
    }

    private int getOffset(int x, int y, int z) {
        // I prefer shifts than multiplies, prefer "bit or" than plus.
        // I know compiler and Android runtime can optimize
        // but this is cool, right?!
        // No, not at all. I saw you filled the shift wrong and debugged for an hour one day.
        return (((x << 4) | z) << 7) | y;
    }

    private int get2dOffset(int x, int z) {
        return (z << 4) | x;
    }

    @Override
    public boolean supportsBlockLightValues() {
        return false;
    }

    @Override
    public boolean supportsHeightMap() {
        return false;
    }

    @Override
    public int getHeightLimit() {
        return 128;
    }

    @Override
    public int getHeightMapValue(int x, int z) {
        if (x >= 16 || z >= 16 || x < 0 || z < 0 || mIsVoid)
            return 0;
        //There's no height map saved here!
        //Do it the hard way!
        for (int offset = POS_BLOCK_IDS + getOffset(x, 127, z), y = 127; y >= 0; y--, offset--) {
            if (mData.get(offset) != 0) return y + 1;
        }
        return 0;
    }

    @Override
    public int getBiome(int x, int z) {
        return mData.get(POS_BIOME_DATA + (get2dOffset(x, z) << 2));
    }

    @Override
    public void setBiome(int x, int z, int id) {
        mData.put(POS_BIOME_DATA + (get2dOffset(x, z) << 2), (byte) id);
    }

    @Override
    public int getGrassColor(int x, int z) {
        int offset = POS_BIOME_DATA + (get2dOffset(x, z) << 2);
        return Color.rgb(mData.get(offset + 1) & 0xff, mData.get(offset + 2) & 0xff, mData.get(offset + 3) & 0xff);
    }

    @NonNull
    @Override
    public Block getBlock(int x, int y, int z) {
        return getBlock(x, y, z, 0);
    }

    @NonNull
    @Override
    public Block getBlock(int x, int y, int z, int layer) {
        return mWorldData.get().mBlockRegistry.createBlock(getKnownBlock(x, y, z, layer));
    }

    @NonNull
    private KnownBlockRepr getKnownBlock(int x, int y, int z, int layer) {
        if (x >= 16 || y >= 128 || z >= 16 || x < 0 || y < 0 || z < 0 || mIsVoid)
            return KnownBlockRepr.B_0_0_AIR;
        int offset = getOffset(x, y, z);
        int id = mData.get(POS_BLOCK_IDS + offset) & 0xff;
        int data = mData.get(POS_META_DATA + (offset >>> 1));
        data = (offset & 1) == 1 ? ((data >>> 4) & 0xf) : (data & 0xf);
        return KnownBlockRepr.getBestBlock(id, data);
    }

    @Override
    public void setBlock(int x, int y, int z, int layer, @NonNull Block block) {
        //TODO implement setBlock for pocket chunk
    }

    @Override
    public int getBlockLightValue(int x, int y, int z) {
        if (x >= 16 || y >= 128 || z >= 16 || x < 0 || y < 0 || z < 0 || mIsVoid)
            return 0;
        int offset = getOffset(x, y, z);
        int dualData = mData.get(POS_BLOCK_LIGHT + (offset >>> 1));
        return (offset & 1) == 1 ? (dualData >>> 4) & 0xf : dualData & 0xf;
    }

    @Override
    public int getSkyLightValue(int x, int y, int z) {
        if (x >= 16 || y >= 128 || z >= 16 || x < 0 || y < 0 || z < 0 || mIsVoid)
            return 0;
        int offset = getOffset(x, y, z);
        int dualData = mData.get(POS_SKY_LIGHT + (offset >>> 1));
        return (offset & 1) == 1 ? (dualData >>> 4) & 0xf : dualData & 0xf;
    }

    @Override
    public int getHighestBlockYUnderAt(int x, int z, int y) {
        for (int yy = y; yy >= 0; yy--) {
            if (getKnownBlock(x & 0xf, yy, z & 0xf, 0) != KnownBlockRepr.B_0_0_AIR) return yy;
        }
        return -1;
    }

    @Override
    public int getCaveYUnderAt(int x, int z, int y) {
        for (int yy = y; yy >= 0; yy--) {
            if (getKnownBlock(x & 0xf, yy, z & 0xf, 0) == KnownBlockRepr.B_0_0_AIR) return yy;
        }
        return -1;
    }

    @Override
    public void save() {
        // TODO implement save for pocket chunk
        if (mIsError || mIsVoid) return;
    }
}
