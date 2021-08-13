package com.mithrilmania.blocktopograph.chunk;

import androidx.annotation.NonNull;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.block.Block;
import com.mithrilmania.blocktopograph.block.BlockTemplate;
import com.mithrilmania.blocktopograph.block.BlockTemplates;
import com.mithrilmania.blocktopograph.block.OldBlock;
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
    public void setBiome(int x, int z, int id) {
    }

    @Override
    public int getGrassColor(int x, int z) {
        return 0;
    }

    @NonNull
    @Override
    public BlockTemplate getBlockTemplate(int x, int y, int z, int layer) {
        return BlockTemplates.getAirTemplate();
    }

    @NonNull
    @Override
    public Block getBlock(int x, int y, int z, int layer) {
        throw new RuntimeException();
    }

    @Override
    public void setBlock(int x, int y, int z, int layer, @NonNull Block block) {
        throw new RuntimeException();
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
