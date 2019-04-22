package com.mithrilmania.blocktopograph.chunk;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.map.Block;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.map.KnownBlock;

import org.jetbrains.annotations.NotNull;

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
    public void setBiome(int x, int z, int id) {
    }

    @Override
    public int getGrassColor(int x, int z) {
        return 0;
    }

    @NotNull
    @Override
    public Block getBlock(int x, int y, int z) {
        return getBlock(x, y, z, 0);
    }

    @NotNull
    @Override
    public Block getBlock(int x, int y, int z, int layer) {
        return getKnownBlock(x, y, z, layer);
    }

    @NotNull
    @Override
    public KnownBlock getKnownBlock(int x, int y, int z, int layer) {
        return KnownBlock.B_0_0_AIR;
    }

    @Override
    public void setBlock(int x, int y, int z, int layer, @NotNull Block block) {
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
