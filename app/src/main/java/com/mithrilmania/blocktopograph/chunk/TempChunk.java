package com.mithrilmania.blocktopograph.chunk;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.terrain.TerrainChunkData;
import com.mithrilmania.blocktopograph.map.Dimension;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicReferenceArray;


public class TempChunk {

//    public final WeakReference<WorldData> worldData;
//
//    public final int x, z;
//    public final Dimension dimension;
//
//    private Version version;
//
//    private AtomicReferenceArray<TerrainChunkData> terrain;
//
//    private volatile NBTChunkData entity, blockEntity;
//
//    public TempChunk(WorldData worldData, int x, int z, Dimension dimension) {
//        this.worldData = new WeakReference<>(worldData);
//        this.x = x;
//        this.z = z;
//        this.dimension = dimension;
//        try {
//            byte[] data = worldData.getChunkData(x, z, ChunkTag.VERSION, dimension, (byte) 0, false);
//            this.version = Version.getVersion(data);
//        } catch (WorldData.WorldDBLoadException | WorldData.WorldDBException e) {
//            e.printStackTrace();
//            this.version = Version.ERROR;
//        }
//        terrain = new AtomicReferenceArray<>(16);
//        Log.e("new Chunk " + x + "," + z);
//    }
//
//    public boolean load2dData() throws Version.VersionException {
//        TerrainChunkData terr = getTerrain((byte) 0);
//        if (null == terr) return false;
//        else return terr.load2DData();
//    }
//
//    public int getHeightMapValue(int x, int z) throws Version.VersionException {
//        return getTerrain((byte) 0).getHeightMapValue(x, z);
//    }
//
//    public int getBiome(int x, int z) throws Version.VersionException {
//        return getTerrain((byte) 0).getBiome(x, z);
//    }
//
//    public int getGrassR(int x, int z) throws Version.VersionException {
//        return getTerrain((byte) 0).getGrassR(x, z);
//    }
//
//    public int getGrassG(int x, int z) throws Version.VersionException {
//        return getTerrain((byte) 0).getGrassG(x, z);
//    }
//
//    public int getGrassB(int x, int z) throws Version.VersionException {
//        return getTerrain((byte) 0).getGrassB(x, z);
//    }
//
//    public int getBlockLightValue(int x, int y, int z) throws Version.VersionException {
//        return getTerrain((byte) (y / version.subChunkHeight)).getBlockLightValue(x, y % version.subChunkHeight, z);
//    }
//
//    public int getBlockTypeId(int x, int y, int z) throws Version.VersionException {
//        return getTerrain((byte) (y / version.subChunkHeight)).getBlockTypeId(x, y % version.subChunkHeight, z);
//    }
//
//    public int getBlockData(int x, int y, int z) throws Version.VersionException {
//        return getTerrain((byte) (y / version.subChunkHeight)).getBlockData(x, y % version.subChunkHeight, z);
//    }
//
//    public boolean supportsBlockLightValues() throws Version.VersionException {
//        return getTerrain((byte) 0).supportsBlockLightValues();
//    }
//
//    public int getHeightLimit() {
//        switch (version) {
//            //This one in lower case good trip leaving for next author.
//            case v0_9:
//            case OLD_LIMITED:
//                return 128;
//            case V1_0:
//            case V1_1:
//            case V1_2_PLUS:
//                return 256;
//            default:
//                return 0;
//        }
//    }
//
//    private TerrainChunkData getTerrain(byte subChunk) throws Version.VersionException {
//        TerrainChunkData data = terrain.get(subChunk & 0xff);
//        if (data == null) {
//            data = this.getVersion().createTerrainChunkData(this, subChunk);
//            terrain.set(subChunk & 0xff, data);
//        }
//        return data;
//    }
//
//    public NBTChunkData getEntity() throws Version.VersionException {
//        if (entity == null) entity = this.getVersion().createEntityChunkData(this);
//        return entity;
//    }
//
//
//    public NBTChunkData getBlockEntity() throws Version.VersionException {
//        if (blockEntity == null) blockEntity = this.getVersion().createBlockEntityChunkData(this);
//        return blockEntity;
//    }
//
//    public Version getVersion() {
//        return version;
//    }
//
//
//    //TODO: should we use the heightmap???
//    public int getHighestBlockYAt(int x, int z) throws Version.VersionException {
//        TerrainChunkData data;
//        for (int subChunk = version.subChunks - 1; subChunk >= 0; subChunk--) {
//            data = this.getTerrain((byte) subChunk);
//            if (data == null || !data.loadTerrain()) continue;
//
//            for (int y = version.subChunkHeight; y >= 0; y--) {
//                if (data.getBlockTypeId(x & 15, y, z & 15) != 0)
//                    return (subChunk * version.subChunkHeight) + y;
//            }
//        }
//        return -1;
//    }
//
//    public int getHighestBlockYUnderAt(int x, int z, int y) throws Version.VersionException {
//        ///Meow
//        ///if (worldData.isMeow()) return meowTeChData.getHighestBlockYUnderAt(x, y, z);
//
//        Version cVersion = getVersion();
//        int offset = y % cVersion.subChunkHeight;
//        int subChunk = y / cVersion.subChunkHeight;
//        TerrainChunkData data;
//
//        for (; subChunk >= 0; subChunk--) {
//            data = this.getTerrain((byte) subChunk);
//            if (data == null || !data.loadTerrain()) continue;
//
//            for (y = offset; y >= 0; y--) {
//                if (data.getBlockTypeId(x & 15, y, z & 15) != 0)
//                    return (subChunk * cVersion.subChunkHeight) + y;
//            }
//
//            //start at the top of the next chunk! (current offset might differ)
//            offset = cVersion.subChunkHeight - 1;
//        }
//        return -1;
//    }
//
//    public int getCaveYUnderAt(int x, int z, int y) throws Version.VersionException {
//        ///Meow
//        ///if (worldData.isMeow()) return meowTeChData.getHighestBlockYUnderAt(x, y, z);
//
//        Version cVersion = getVersion();
//        int offset = y % cVersion.subChunkHeight;
//        int subChunk = y / cVersion.subChunkHeight;
//        TerrainChunkData data;
//
//        for (; subChunk >= 0; subChunk--) {
//            data = this.getTerrain((byte) subChunk);
//            if (data == null || !data.loadTerrain()) continue;
//            for (y = offset; y >= 0; y--) {
//                if (data.getBlockTypeId(x & 15, y, z & 15) == 0)
//                    return (subChunk * cVersion.subChunkHeight) + y;
//            }
//
//            //start at the top of the next chunk! (current offset might differ)
//            offset = cVersion.subChunkHeight - 1;
//        }
//        return -1;
//    }


}
