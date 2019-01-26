package com.mithrilmania.blocktopograph.chunk.terrain;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.ChunkTag;

import java.io.IOException;
import java.nio.ByteBuffer;


public class V0_9_TerrainChunkData extends TerrainChunkData {


    public volatile ByteBuffer buf;

    public static final int chunkW = 16, chunkL = 16, chunkH = 128;

    public static final int area = chunkW * chunkL;
    public static final int vol = area * chunkH;

    public static final int POS_BLOCK_IDS = 0;
    public static final int POS_META_DATA = POS_BLOCK_IDS + vol;
    public static final int POS_SKY_LIGHT = POS_META_DATA + (vol >> 1);
    public static final int POS_BLOCK_LIGHT = POS_SKY_LIGHT + (vol >> 1);

    public static final int POS_HEIGHTMAP = POS_BLOCK_LIGHT + (vol >> 1);

    public static final int POS_BIOME_DATA = POS_HEIGHTMAP + area;

    public static final int LENGTH = POS_BIOME_DATA + (area * 4);

    public V0_9_TerrainChunkData(Chunk chunk, byte subChunk) {
        super(chunk, subChunk);
        mNotFailed = tryLoad();
    }

    @Override
    public void write() throws IOException, WorldData.WorldDBException {
        Chunk chunk = this.chunk.get();
        chunk.getWorldData().writeChunkData(chunk.mChunkX, chunk.mChunkZ, ChunkTag.V0_9_LEGACY_TERRAIN, chunk.mDimension, subChunk, false, toByteArray());
    }

    @Override
    public boolean loadTerrain() {
        return mNotFailed;
    }

    @Override
    public boolean load2DData() {
        return mNotFailed;
    }

    public boolean tryLoad() {
        if (buf == null) {
            try {
                Chunk chunk = this.chunk.get();
                byte[] rawData = chunk.getWorldData().getChunkData(chunk.mChunkX, chunk.mChunkZ, ChunkTag.V0_9_LEGACY_TERRAIN, chunk.mDimension, subChunk, false);
                if (rawData == null) return false;
                this.buf = ByteBuffer.wrap(rawData);
                return true;
            } catch (Exception e) {
                //data is not present
                return false;
            }
        } else return true;
    }

    public byte[] toByteArray() throws IOException {
        //should return the original rawData array
        return buf.array();
    }


    @Override
    public void createEmpty() {
        byte[] chunk = new byte[LENGTH];
        int x, y, z, i = 0;
        byte bedrock = (byte) 7;
        byte sandstone = (byte) 24;

        //generate super basic terrain (one layer of bedrock, 31 layers of sandstone)
        for (x = 0; x < chunkW; x++) {
            for (z = 0; z < chunkL; z++) {
                for (y = 0; y < chunkH; y++, i++) {
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


        this.buf = ByteBuffer.wrap(chunk);
    }


    @Override
    public byte getBlockTypeId(int x, int y, int z) {
        if (x >= chunkW || y >= chunkH || z >= chunkL || x < 0 || y < 0 || z < 0) {
            return 0;
        }
        return buf.get(POS_BLOCK_IDS + getOffset(x, y, z));
    }

    @Override
    public byte getBlockData(int x, int y, int z) {
        if (x >= chunkW || y >= chunkH || z >= chunkL || x < 0 || y < 0 || z < 0) {
            return 0;
        }
        int offset = getOffset(x, y, z);
        byte dualData = buf.get(POS_META_DATA + (offset >>> 1));
        return (byte) ((offset & 1) == 1 ? ((dualData >>> 4) & 0xf) : (dualData & 0xf));
    }

    @Override
    public byte getSkyLightValue(int x, int y, int z) {
        if (x >= chunkW || y >= chunkH || z >= chunkL || x < 0 || y < 0 || z < 0) {
            return 0;
        }
        int offset = getOffset(x, y, z);
        byte dualData = buf.get(POS_SKY_LIGHT + (offset >>> 1));
        return (byte) ((offset & 1) == 1 ? (dualData >>> 4) & 0xf : dualData & 0xf);
    }

    @Override
    public byte getBlockLightValue(int x, int y, int z) {
        if (x >= chunkW || y >= chunkH || z >= chunkL || x < 0 || y < 0 || z < 0) {
            return 0;
        }
        int offset = getOffset(x, y, z);
        byte dualData = buf.get(POS_BLOCK_LIGHT + (offset >>> 1));
        return (byte) ((offset & 1) == 1 ? (dualData >>> 4) & 0xf : dualData & 0xf);
    }

    @Override
    public boolean supportsBlockLightValues() {
        return true;
    }

    /**
     * Sets a block type, and also set the corresponding dirty table entry and set the saving flag.
     */
    @Override
    public void setBlockTypeId(int x, int y, int z, int type) {
        if (x >= chunkW || y >= chunkH || z >= chunkL || x < 0 || y < 0 || z < 0) {
            return;
        }
        buf.put(POS_BLOCK_IDS + getOffset(x, y, z), (byte) type);
    }

    @Override
    public void setBlockData(int x, int y, int z, int newData) {
        if (x >= chunkW || y >= chunkH || z >= chunkL || x < 0 || y < 0 || z < 0) {
            return;
        }
        int offset = getOffset(x, y, z);
        int pos = POS_META_DATA + (offset >> 1);
        byte oldData = buf.get(POS_META_DATA + (offset >> 1));
        if ((offset & 1) == 1) {
            buf.put(pos, (byte) ((newData << 4) | (oldData & 0xf)));
        } else {
            buf.put(pos, (byte) ((oldData & 0xf0) | (newData & 0xf)));
        }
    }

    private int getOffset(int x, int y, int z) {
        return (x * chunkW + z) * chunkH + y;
    }

    @Override
    public byte getBiome(int x, int z) {
        return buf.get(POS_BIOME_DATA + (get2Di(x, z) * 4));
    }

    @Override
    public byte getGrassR(int x, int z) {
        return buf.get(POS_BIOME_DATA + (get2Di(x, z) * 4) + 1);
    }

    @Override
    public byte getGrassG(int x, int z) {
        return buf.get(POS_BIOME_DATA + (get2Di(x, z) * 4) + 2);
    }

    @Override
    public byte getGrassB(int x, int z) {
        return buf.get(POS_BIOME_DATA + (get2Di(x, z) * 4) + 3);
    }

    private int get2Di(int x, int z) {
        return z * chunkL + x;
    }

    @Override
    public int getHeightMapValue(int x, int z) {
        return buf.get(POS_HEIGHTMAP + get2Di(x, z)) & 0xff;
    }

}
