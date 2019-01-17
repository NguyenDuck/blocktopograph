package com.mithrilmania.blocktopograph.chunk.terrain;

import android.support.v4.graphics.ColorUtils;
import android.util.Log;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.util.Color;

import java.io.IOException;

public class MeowTeChData extends TerrainChunkData {

    private com.litl.leveldb.Chunk nativeChunk;

    public MeowTeChData(Chunk chunk) {
        super(chunk, (byte) 1);
    }

    @Override
    public boolean loadTerrain() {
        if (nativeChunk == null)
            nativeChunk = new com.litl.leveldb.Chunk(
                    this.chunk.worldData.db, this.chunk.x << 4,
                    this.chunk.z << 4, this.chunk.dimension.id);
        if (nativeChunk.isDead()) {
            nativeChunk = null;
            return false;
        }
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
        return -1;
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

    private Color getColor(int x, int z) {
        double amp = Math.sin(x * z * z) + 1.2 * Math.cos(z) + 0.2 * Math.sin(x / 2.0) + 0.1 * Math.sin(z / 4.0);
        float[] arr = new float[]{96, (float) (0.54 + amp * 2.0 / 25.0), (float) (0.48 + (0.54 + amp * 2.0 / 30.0))};
        return Color.fromRGB(ColorUtils.HSLToColor(arr));//Wrong
    }

    @Override
    public byte getGrassR(int x, int z) {
        //Color c = getColor(x, z);
        //Log.e("123", c.toString());
        return 0x56;//(byte) c.red;
    }

    @Override
    public byte getGrassG(int x, int z) {
        return (byte) 0xf9;//(byte) getColor(x, z).green;
    }

    @Override
    public byte getGrassB(int x, int z) {
        return 0x1a;//(byte) getColor(x, z).blue;
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
