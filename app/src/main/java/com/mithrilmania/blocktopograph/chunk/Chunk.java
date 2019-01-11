package com.mithrilmania.blocktopograph.chunk;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.terrain.MeowTeChData;
import com.mithrilmania.blocktopograph.chunk.terrain.TerrainChunkData;
import com.mithrilmania.blocktopograph.map.Dimension;

import java.util.concurrent.atomic.AtomicReferenceArray;


public class Chunk {

    public final WorldData worldData;

    public final int x, z;
    public final Dimension dimension;

    private Version version;

    ///Meow
    private AtomicReferenceArray<TerrainChunkData> terrain;
    private MeowTeChData meowTeChData;

    private volatile NBTChunkData entity, blockEntity;

    public Chunk(WorldData worldData, int x, int z, Dimension dimension) {
        this.worldData = worldData;
        this.x = x;
        this.z = z;
        this.dimension = dimension;

        ///Meow
        terrain = new AtomicReferenceArray<>(256);
    }

    public TerrainChunkData getTerrain(byte subChunk) throws Version.VersionException {
        TerrainChunkData data = terrain.get(subChunk & 0xff);
        if (data == null) {
            data = this.getVersion().createTerrainChunkData(this, subChunk);
            terrain.set(subChunk & 0xff, data);
        }
        return data;
    }

    public NBTChunkData getEntity() throws Version.VersionException {
        if (entity == null) entity = this.getVersion().createEntityChunkData(this);
        return entity;
    }


    public NBTChunkData getBlockEntity() throws Version.VersionException {
        if (blockEntity == null) blockEntity = this.getVersion().createBlockEntityChunkData(this);
        return blockEntity;
    }

    public Version getVersion() {
        if (this.version == null) try {
            byte[] data = this.worldData.getChunkData(x, z, ChunkTag.VERSION, dimension, (byte) 0, false);
            this.version = Version.getVersion(data);
        } catch (WorldData.WorldDBLoadException | WorldData.WorldDBException e) {
            e.printStackTrace();
            this.version = Version.ERROR;
        }

        return this.version;
    }


    //TODO: should we use the heightmap???
    public int getHighestBlockYAt(int x, int z) throws Version.VersionException {
        Version cVersion = getVersion();
        TerrainChunkData data;
        for (int subChunk = cVersion.subChunks - 1; subChunk >= 0; subChunk--) {
            data = this.getTerrain((byte) subChunk);
            if (data == null || !data.loadTerrain()) continue;

            for (int y = cVersion.subChunkHeight; y >= 0; y--) {
                if (data.getBlockTypeId(x & 15, y, z & 15) != 0)
                    return (subChunk * cVersion.subChunkHeight) + y;
            }
        }
        return -1;
    }

    public int getHighestBlockYUnderAt(int x, int z, int y) throws Version.VersionException {
        Version cVersion = getVersion();
        int offset = y % cVersion.subChunkHeight;
        int subChunk = y / cVersion.subChunkHeight;
        TerrainChunkData data;

        for (; subChunk >= 0; subChunk--) {
            data = this.getTerrain((byte) subChunk);
            if (data == null || !data.loadTerrain()) continue;

            for (y = offset; y >= 0; y--) {
                if (data.getBlockTypeId(x & 15, y, z & 15) != 0)
                    return (subChunk * cVersion.subChunkHeight) + y;
            }

            //start at the top of the next chunk! (current offset might differ)
            offset = cVersion.subChunkHeight - 1;
        }
        return -1;
    }

    public int getCaveYUnderAt(int x, int z, int y) throws Version.VersionException {
        Version cVersion = getVersion();
        int offset = y % cVersion.subChunkHeight;
        int subChunk = y / cVersion.subChunkHeight;
        TerrainChunkData data;

        for (; subChunk >= 0; subChunk--) {
            data = this.getTerrain((byte) subChunk);
            if (data == null || !data.loadTerrain()) continue;
            for (y = offset; y >= 0; y--) {
                if (data.getBlockTypeId(x & 15, y, z & 15) == 0)
                    return (subChunk * cVersion.subChunkHeight) + y;
            }

            //start at the top of the next chunk! (current offset might differ)
            offset = cVersion.subChunkHeight - 1;
        }
        return -1;
    }


}
