package com.mithrilmania.blocktopograph.chunk;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.map.Dimension;

public final class VoidChunk extends Chunk {

    VoidChunk(WorldData worldData, Version version, int chunkX, int chunkZ, Dimension dimension) {
        super(worldData, version, chunkX, chunkZ, dimension);
        mIsVoid = true;
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
        return 0;
    }

    @Override
    public int getHeightMapValue(int x, int z) {
        return 0;
    }

    @Override
    public int getBiome(int x, int z) {
        return 0;
    }

    @Override
    public int getGrassColor(int x, int z) {
        return 0;
    }

    @Override
    public int getBlockRuntimeId(int x, int y, int z) {
        return 0;
    }

    @Override
    public int getBlockRuntimeId(int x, int y, int z, int layer) {
        return 0;
    }

    @Override
    public void setBlockRuntimeId(int x, int y, int z, int layer, int runtimeId) {
    }

    @Override
    public int getBlockLightValue(int x, int y, int z) {
        return 0;
    }

    @Override
    public int getSkyLightValue(int x, int y, int z) {
        return 0;
    }

    @Override
    public int getHighestBlockYUnderAt(int x, int z, int y) {
        return -1;
    }

    @Override
    public int getCaveYUnderAt(int x, int z, int y) {
        return -1;
    }

    @Override
    public void save() {
    }
}
