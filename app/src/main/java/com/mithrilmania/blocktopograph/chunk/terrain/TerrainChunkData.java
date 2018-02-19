package com.mithrilmania.blocktopograph.chunk.terrain;

import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.ChunkData;


public abstract class TerrainChunkData extends ChunkData {

    public final byte subChunk;

    public TerrainChunkData(Chunk chunk, byte subChunk) {
        super(chunk);
        this.subChunk = subChunk;
    }

    public abstract boolean loadTerrain();

    public abstract boolean load2DData();

    public abstract byte getBlockTypeId(int x, int y, int z);

    public abstract byte getBlockData(int x, int y, int z);

    public abstract byte getSkyLightValue(int x, int y, int z);

    public abstract byte getBlockLightValue(int x, int y, int z);

    public abstract boolean supportsBlockLightValues();

    public abstract void setBlockTypeId(int x, int y, int z, int type);

    public abstract void setBlockData(int x, int y, int z, int newData);

    public abstract byte getBiome(int x, int z);

    public abstract byte getGrassR(int x, int z);

    public abstract byte getGrassG(int x, int z);

    public abstract byte getGrassB(int x, int z);

    public abstract int getHeightMapValue(int x, int z);

    
}
