package com.mithrilmania.blocktopograph;

import com.mithrilmania.blocktopograph.map.MarkerManager;
import com.mithrilmania.blocktopograph.nbt.convert.LevelDataConverter;
import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;
import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.IntTag;
import com.mithrilmania.blocktopograph.nbt.tags.LongTag;
import com.mithrilmania.blocktopograph.util.io.TextFile;
import com.litl.leveldb.Iterator;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class World implements Serializable {

    // The World (just a WorldData handle) is serializable, this is the tag used in the android workflow
    public static final String ARG_WORLD_SERIALIZED = "world_ser";
    private static final long serialVersionUID = 792709417041090031L;
    public final String worldName;
    public final File worldFolder;
    public final File levelFile;
    public CompoundTag level;
    private transient WorldData worldData;
    private MarkerManager markersManager;

    public World(File worldFolder) throws WorldLoadException {

        if (!worldFolder.exists())
            throw new WorldLoadException("Error: '" + worldFolder.getPath() + "' does not exist!");

        this.worldFolder = worldFolder;

        // check for a custom world name
        File levelNameTxt = new File(this.worldFolder, "levelname.txt");
        if (levelNameTxt.exists())
            worldName = TextFile.readTextFileFirstLine(levelNameTxt);// new way of naming worlds
        else worldName = this.worldFolder.getName();// legacy way of naming worlds


        this.levelFile = new File(this.worldFolder, "level.dat");
        if (!levelFile.exists())
            throw new WorldLoadException("Error: Level-file: '" + levelFile.getPath() + "' does not exist!");

        try {
            this.level = LevelDataConverter.read(levelFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new WorldLoadException("Error: failed to read level: '" + levelFile.getPath() + "' !");
        }

    }

    public String getWorldDisplayName() {
        if (worldName == null) return null;
        //return worldname, without special color codes
        // (character prefixed by the section-sign character)
        // Short quick regex, shouldn't affect performance too much
        return worldName.replaceAll("\u00A7.", "");
    }

    public long getWorldSeed() {
        if (this.level == null) return 0;

        LongTag seed = (LongTag) this.level.getChildTagByKey("RandomSeed");
        return seed == null ? 0 : seed.getValue();
    }

    public void writeLevel(CompoundTag level) throws IOException {
        LevelDataConverter.write(level, this.levelFile);
        this.level = level;
    }

    public MarkerManager getMarkerManager() {
        if (markersManager == null)
            markersManager = new MarkerManager(new File(this.worldFolder, "markers.txt"));

        return markersManager;
    }

    /**
     * @return worldFolder name, also unique save-file ID
     */
    public String getID() {
        return this.worldFolder.getName();
    }

    public WorldData getWorldData() {
        if (this.worldData == null) {
            this.worldData = new WorldData(this);
        }
        return this.worldData;
    }

    public void closeDown() throws WorldData.WorldDBException {
        if (this.worldData != null) this.worldData.closeDB();
    }

    public void pause() throws WorldData.WorldDBException {
        closeDown();
    }

    /*
    public byte[] loadChunkData(int chunkX, int chunkZ, ChunkTag dataType, Dimension dimension) throws WorldData.WorldDBLoadException, WorldData.WorldDBException {
        return getWorldData().getChunkData(chunkX, chunkZ, dataType, dimension);
    }

    public void saveChunkData(int chunkX, int chunkZ, ChunkData chunkData) throws IOException, WorldData.WorldDBException {
        byte[] bData = chunkData.toByteArray();
        if (bData != null) getWorldData().writeChunkData(chunkX, chunkZ, chunkData.dataType, bData, chunkData.dimension);
        else getWorldData().removeChunkData(chunkX, chunkZ, chunkData.dataType, chunkData.dimension);
    }


    //returns true if creating and saving was successful
    public ChunkData createEmptyChunkData(int chunkX, int chunkZ, ChunkTag dataType, Dimension dimension){

        ChunkData data = dataType.newInstance(dimension);
        if(data == null) return null;

        data.createEmpty();

        try {
            this.saveChunkData(chunkX, chunkZ, data);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    */

    public void resume() throws WorldData.WorldDBException {

        this.getWorldData().openDB();

        //logDBKeys();

    }

    //function meant for debugging, not used in production
    public void logDBKeys() {
        try {
            this.getWorldData();

            worldData.openDB();

            Iterator it = worldData.db.iterator();

            for (it.seekToFirst(); it.isValid(); it.next()) {
                byte[] key = it.getKey();
                byte[] value = it.getValue();
                /*if(key.length == 9 && key[8] == RegionDataType.TERRAIN.dataID) */
                Log.d("key: " + new String(key) + " key in Hex: " + WorldData.bytesToHex(key, 0, key.length) + " size: " + value.length);

            }

            it.close();

        } catch (WorldData.WorldDBException e) {
            e.printStackTrace();
        }
    }

    public enum SpecialDBEntryType {

        //Who came up with the formatting for these NBT keys is CRAZY
        // (PascalCase, camelCase, snake_case, lowercase, m-prefix(Android), tilde-prefix; it's all there!)
        BIOME_DATA("BiomeData"),
        OVERWORLD("Overworld"),
        M_VILLAGES("mVillages"),
        PORTALS("portals"),
        LOCAL_PLAYER("~local_player"),
        AUTONOMOUS_ENTITIES("AutonomousEntities"),
        DIMENSION_0("dimension0"),
        DIMENSION_1("dimension1"),
        DIMENSION_2("dimension2");

        public final String keyName;
        public final byte[] keyBytes;

        SpecialDBEntryType(String keyName) {
            this.keyName = keyName;
            this.keyBytes = keyName.getBytes(NBTConstants.CHARSET);
        }
    }

    public static class WorldLoadException extends Exception {
        private static final long serialVersionUID = 1812348294537392782L;

        public WorldLoadException(String msg) {
            super(msg);
        }
    }
}
