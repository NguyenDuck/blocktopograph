package com.mithrilmania.blocktopograph.chunk;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.terrain.TerrainChunkData;
import com.mithrilmania.blocktopograph.map.Dimension;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicReferenceArray;


public class Chunk {

    public final WeakReference<WorldData> worldData;

    public final int x, z;
    public final Dimension dimension;

    private Version version;

    private AtomicReferenceArray<TerrainChunkData> terrain;

    private volatile NBTChunkData entity, blockEntity;

    public Chunk(WorldData worldData, int x, int z, Dimension dimension) {
        this.worldData = new WeakReference<>(worldData);
        this.x = x;
        this.z = z;
        this.dimension = dimension;
        terrain = new AtomicReferenceArray<>(16);
        Log.e("new Chunk " + x + "," + z);
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
        ///Meow
        ///if (worldData.isMeow()) return Version.V1_1;

        if (this.version == null) try {
            WorldData worldData = this.worldData.get();
            if (worldData == null) return Version.ERROR;
            byte[] data = worldData.getChunkData(x, z, ChunkTag.VERSION, dimension, (byte) 0, false);
            this.version = Version.getVersion(data);
        } catch (WorldData.WorldDBLoadException | WorldData.WorldDBException e) {
            e.printStackTrace();
            this.version = Version.ERROR;
        }

        return this.version;
    }


    //TODO: should we use the heightmap???
    public int getHighestBlockYAt(int x, int z) throws Version.VersionException {
        ///Meow
        ///if (worldData.isMeow()) return meowTeChData.getHighestBlockYUnderAt(x, 255, z);

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
        ///Meow
        ///if (worldData.isMeow()) return meowTeChData.getHighestBlockYUnderAt(x, y, z);

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
        ///Meow
        ///if (worldData.isMeow()) return meowTeChData.getHighestBlockYUnderAt(x, y, z);

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
