package com.mithrilmania.blocktopograph.chunk.terrain;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.Chunk;

import java.io.IOException;

public class MeowTeChData extends TerrainChunkData {

    public MeowTeChData(Chunk chunk) {
        super(chunk, (byte) 0);
    }

    @Override
    public boolean loadTerrain() {
        return true;
    }

    @Override
    public boolean load2DData() {
        return true;
    }

    @Override
    public byte getBlockTypeId(int x, int y, int z) {
        return 9;
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
        return 1;
    }

    @Override
    public void createEmpty() {

    }

    @Override
    public void write() throws IOException, WorldData.WorldDBException {

    }
}
