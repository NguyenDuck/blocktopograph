package com.mithrilmania.blocktopograph.chunk.terrain;

import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.ChunkData;
import com.mithrilmania.blocktopograph.util.Noise;


public abstract class TerrainChunkData extends ChunkData {

    public final byte subChunk;

    protected boolean mNotFailed;

    public TerrainChunkData(Chunk chunk, byte subChunk) {
        super(chunk);
        this.mNotFailed = true;
        this.subChunk = subChunk;
    }

    public final boolean hasNotFailed() {
        return mNotFailed;
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

    protected int getNoise(int base, int x, int z) {
        // noise values are between -1 and 1
        // 0.0001 is added to the coordinates because integer values result in 0
        Chunk chunk = this.chunk.get();
        double oct1 = Noise.noise(
                ((double) (chunk.mChunkX * 16 + x) / 100.0) + 0.0001,
                ((double) (chunk.mChunkZ * 16 + z) / 100.0) + 0.0001);
        double oct2 = Noise.noise(
                ((double) (chunk.mChunkX * 16 + x) / 20.0) + 0.0001,
                ((double) (chunk.mChunkZ * 16 + z) / 20.0) + 0.0001);
        double oct3 = Noise.noise(
                ((double) (chunk.mChunkX * 16 + x) / 3.0) + 0.0001,
                ((double) (chunk.mChunkZ * 16 + z) / 3.0) + 0.0001);
        return (int) (base + 60 + (40 * oct1) + (14 * oct2) + (6 * oct3));
    }


}
