package com.mithrilmania.blocktopograph.chunk.terrain;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.block.KnownBlockRepr;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.ChunkTag;
import com.mithrilmania.blocktopograph.map.Biome;
import com.mithrilmania.blocktopograph.nbt.convert.NBTInputStream;
import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.ShortTag;
import com.mithrilmania.blocktopograph.nbt.tags.StringTag;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class V1_2_Plus_TerrainChunkData extends TerrainChunkData {

    //There could be multiple BlockStorage but we can just display the main.
    public volatile IntBuffer mainStorage;
    public volatile ByteBuffer data2D;
    public volatile List<Integer> palette;
    public volatile int blockTypes, blockCodeLenth;

    public static final int chunkW = 16, chunkL = 16, chunkH = 16;

    public static final int area = chunkW * chunkL;
    public static final int vol = area * chunkH;

    public static final int POS_HEIGHTMAP = 0;
    // it looks like each biome takes 2 bytes, and the first 1 byte of every 2 bytes is always 0!?
    public static final int POS_BIOME_DATA = POS_HEIGHTMAP + area + area;
    public static final int DATA2D_LENGTH = POS_BIOME_DATA + area;

    //Masks used to extract BlockState bits of a certain block out of a int32, and vice-versa.
    private static final int[] msk = {0b1, 0b11, 0b111, 0b1111, 0b11111, 0b111111, 0b1111111,
            0b11111111,
            0b111111111, 0b1111111111, 0b11111111111,
            0b111111111111,
            0b1111111111111, 0b11111111111111, 0b11111111111111};

    public V1_2_Plus_TerrainChunkData(Chunk chunk, byte subChunk) {
        super(chunk, subChunk);
        mNotFailed = loadTerrain();
    }

    @Override
    public void write() throws WorldData.WorldDBException {
        Chunk chunk = this.chunk.get();
        //this.chunk.worldData.get().writeChunkData(chunk.mChunkX, chunk.mChunkZ, ChunkTag.TERRAIN, chunk.mDimension, subChunk, true, terrainData.array());
        chunk.getWorldData().writeChunkData(chunk.mChunkX, chunk.mChunkZ, ChunkTag.DATA_2D, chunk.mDimension, subChunk, true, data2D.array());
    }

    @Override
    public boolean loadTerrain() {
        //Don't repeat the work.
        if (mainStorage == null) {
            try {
                //Retrieve raw data from database.
                Chunk chunk = this.chunk.get();
                byte[] rawData = chunk.getWorldData().getChunkData(chunk.mChunkX, chunk.mChunkZ, ChunkTag.TERRAIN, chunk.mDimension, subChunk, true);
                if (rawData == null) return false;
                ByteBuffer raw = ByteBuffer.wrap(rawData);
                raw.order(ByteOrder.LITTLE_ENDIAN);

                //The first byte indicates version.
                switch (rawData[0]) {
                    //1: Only one BlockStorage starting from the next byte.
                    case 1:
                        raw.position(1);
                        break;
                    //8: One or more BlockStorage's, next byte is the count. We only read the first.
                    case 8:
                        raw.position(2);
                        break;
                    //0,2,3,4,5,6,7: Should use a V1_1 terrain, why reaching here?
                    //Else: wtf?
                    default:
                        return false;
                }

                //Load the BlockStorage.
                loadBlockStorage(raw);
                return true;
            } catch (Exception e) {
                //data is not present
                return false;
            }
        } else return mNotFailed;
    }

    private void loadBlockStorage(ByteBuffer raw) throws IOException {

        //Read BlockState length.
        //this byte = (length << 2) | serializedType.
        blockCodeLenth = (raw.get() & 0xff) >> 1;

        //We use this much of bytes to store BlockStates.
        int bufsize = (4095 / (32 / blockCodeLenth) + 1) << 2;
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufsize);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        mainStorage = byteBuffer.asIntBuffer();

        //No convenient way copy these stuff.
        byteBuffer.put(raw.array(), raw.position(), bufsize);
        raw.position(raw.position() + bufsize);

        //Palette items count.
        int psize = raw.getInt();

        //Construct the palette. Each item is a piece of nbt data.
        palette = new ArrayList<>(16);

        //NBT reader requires a stream.
        ByteArrayInputStream bais = new ByteArrayInputStream(raw.array());
        bais.skip(raw.position());

        //Wrap it.
        NBTInputStream nis = new NBTInputStream(bais, false);
        for (int i = 0; i < psize; i++) {

            //Read a piece of nbt data, represented by a root CompoundTag.
            CompoundTag tag = (CompoundTag) nis.readTag();

            //Read `name` and `val` then resolve the `name` into numeric id.
            String name = ((StringTag) tag.getChildTagByKey("name")).getValue();
            int data = ((ShortTag) tag.getChildTagByKey("val")).getValue();
            palette.add(
//                    BlockNameResolver.resolve(name)
                    KnownBlockRepr.resolve(name)
                            << 8 | data);
        }

        //If one day we need to read more BlockStorage's, this line helps.
        raw.position(raw.position() + nis.getReadCount());
    }

    @Override
    public boolean load2DData() {
        if (data2D == null) {
            try {
                Chunk chunk = this.chunk.get();
                byte[] rawData = chunk.getWorldData().getChunkData(chunk.mChunkX, chunk.mChunkZ, ChunkTag.DATA_2D, chunk.mDimension, subChunk, false);
                if (rawData == null) return false;
                this.data2D = ByteBuffer.wrap(rawData);
                return true;
            } catch (Exception e) {
                //data is not present
                return false;
            }
        } else return true;
    }


    @Override
    public void createEmpty() {

        int i = 0;

        if (this.subChunk == (byte) 0) {

            byte[] data2d = new byte[DATA2D_LENGTH];

            //fill heightmap
            for (; i < POS_BIOME_DATA; ) {
                data2d[i++] = 0;
                data2d[i++] = 32;
            }

            //fill biome data
            for (; i < DATA2D_LENGTH; ) {
                data2d[i++] = 1;//biome: plains
                data2d[i++] = (byte) 42;//r
                data2d[i++] = (byte) 42;//g
                data2d[i++] = (byte) 42;//b
            }

            this.data2D = ByteBuffer.wrap(data2d);
        }


    }

    private int getBlockState(int x, int y, int z) {

        if (!mNotFailed) return 0;

        //The codeOffset'th BlockState is wanted.
        int codeOffset = getOffset(x, y, z);

        //How much BlockStates can one int32 hold?
        int intCapa = 32 / blockCodeLenth;

        //The int32 that holds the wanted BlockState.
        int stick = mainStorage.get(codeOffset / intCapa);

        //Get the BlockState. It's also the index in palette array.
        int ind = (stick >> (codeOffset % intCapa * blockCodeLenth)) & msk[blockCodeLenth - 1];

        //Transform the local BlockState into global id<<8|data structure.
        return palette.get(ind);
    }


    @Override
    public byte getBlockTypeId(int x, int y, int z) {
        if (x >= chunkW || y >= chunkH || z >= chunkL || x < 0 || y < 0 || z < 0) {
            return 0;
        }
        return (byte) (getBlockState(x, y, z) >>> 8);
    }

    @Override
    public byte getBlockData(int x, int y, int z) {
        if (x >= chunkW || y >= chunkH || z >= chunkL || x < 0 || y < 0 || z < 0) {
            return 0;
        }
        return (byte) (getBlockState(x, y, z) & 0xf);
    }

    @Override
    public byte getSkyLightValue(int x, int y, int z) {
        if (x >= chunkW || y >= chunkH || z >= chunkL || x < 0 || y < 0 || z < 0) {
            return 0;
        }
        return 0;
    }

    @Override
    public byte getBlockLightValue(int x, int y, int z) {
        //block light is not stored anymore
        return 0;
    }

    @Override
    public boolean supportsBlockLightValues() {
        return false;
    }

    /**
     * Sets a block type, and also set the corresponding dirty table entry and set the saving flag.
     */
    @Override
    public void setBlockTypeId(int x, int y, int z, int type) {
        if (x >= chunkW || y >= chunkH || z >= chunkL || x < 0 || y < 0 || z < 0) {
            return;
        }
    }

    @Override
    public void setBlockData(int x, int y, int z, int newData) {
        if (x >= chunkW || y >= chunkH || z >= chunkL || x < 0 || y < 0 || z < 0) {
            return;
        }
    }

    private int getOffset(int x, int y, int z) {
        return (((x << 4) | z) << 4) | y;
    }

    @Override
    public byte getBiome(int x, int z) {
        return data2D.get(POS_BIOME_DATA + get2Di(x, z));
    }

    @Override
    public byte getGrassR(int x, int z) {
        Biome biome = Biome.getBiome(getBiome(x, z) & 0xff);
        int res = getNoise(30 + (biome.color.red / 5), x, z);
        return (byte) (res > 0xff ? 0xff : (res < 0 ? 0 : res));
    }

    @Override
    public byte getGrassG(int x, int z) {
        Biome biome = Biome.getBiome(getBiome(x, z) & 0xff);
        int res = getNoise(120 + (biome.color.green / 5), x, z);
        return (byte) (res > 0xff ? 0xff : (res < 0 ? 0 : res));
    }

    @Override
    public byte getGrassB(int x, int z) {
        Biome biome = Biome.getBiome(getBiome(x, z) & 0xff);
        int res = getNoise(30 + (biome.color.blue / 5), x, z);
        return (byte) (res > 0xff ? 0xff : (res < 0 ? 0 : res));
    }

    private int get2Di(int x, int z) {
        return z * chunkL + x;
    }

    @Override
    public int getHeightMapValue(int x, int z) {
        short h = data2D.getShort(POS_HEIGHTMAP + (get2Di(x, z) << 1));
        return ((h & 0xff) << 8) | ((h >> 8) & 0xff);//little endian to big endian
    }
}
