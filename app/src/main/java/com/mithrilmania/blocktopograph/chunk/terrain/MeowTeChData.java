package com.mithrilmania.blocktopograph.chunk.terrain;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.Chunk;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class MeowTeChData extends TerrainChunkData {

    private com.litl.leveldb.Chunk nativeChunk;

    public MeowTeChData(Chunk chunk) {
        super(chunk, (byte) 1);
    }

    @Override
    public boolean loadTerrain() {
        if (nativeChunk == null)
            nativeChunk = new com.litl.leveldb.Chunk(this.chunk.worldData.db, this.chunk.x << 4, this.chunk.z << 4, this.chunk.dimension.id);
        return true;
    }

    @Override
    public boolean load2DData() {
        return loadTerrain();
    }

    @Override
    public byte getBlockTypeId(int x, int y, int z) {
        int val = nativeChunk.getBlock(x, y, z);
        byte b = (byte) (val >> 8);
        return b;
    }

    public int getHighestBlockYUnderAt(int x, int y, int z) {
        for (int yy = y; yy >= 0; yy--) {
            int val = nativeChunk.getBlock(x, y, z);
            if (val != 0)
                return yy;
        }
        return 0;
    }

    @Override
    public byte getBlockData(int x, int y, int z) {
        return 0;
    }

    @Override
    public byte getSkyLightValue(int x, int y, int z) {
        return 0;
    }

    @Override
    public byte getBlockLightValue(int x, int y, int z) {
        return 0;
    }

    @Override
    public boolean supportsBlockLightValues() {
        return false;
    }

    @Override
    public void setBlockTypeId(int x, int y, int z, int type) {

    }

    @Override
    public void setBlockData(int x, int y, int z, int newData) {

    }

    @Override
    public byte getBiome(int x, int z) {
        return 1;
    }

    @Override
    public byte getGrassR(int x, int z) {
        return -128;
    }

    @Override
    public byte getGrassG(int x, int z) {
        return 0;
    }

    @Override
    public byte getGrassB(int x, int z) {
        return 0;
    }

    @Override
    public int getHeightMapValue(int x, int z) {
        return getHighestBlockYUnderAt(x, 255, z);
    }

    @Override
    public void createEmpty() {

    }

    @Override
    public void write() throws IOException, WorldData.WorldDBException {

    }
}
