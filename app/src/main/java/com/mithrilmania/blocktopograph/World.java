package com.mithrilmania.blocktopograph;

import android.os.Bundle;
import android.util.SparseIntArray;

import com.mithrilmania.blocktopograph.map.MarkerManager;
import com.mithrilmania.blocktopograph.nbt.convert.LevelDataConverter;
import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;
import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.IntArrayTag;
import com.mithrilmania.blocktopograph.nbt.tags.IntTag;
import com.mithrilmania.blocktopograph.nbt.tags.ListTag;
import com.mithrilmania.blocktopograph.nbt.tags.LongTag;
import com.mithrilmania.blocktopograph.nbt.tags.StringTag;
import com.mithrilmania.blocktopograph.nbt.tags.Tag;
import com.mithrilmania.blocktopograph.util.io.TextFile;
import com.litl.leveldb.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;

public class World implements Serializable {

    // The World (just a WorldData handle) is serializable, this is the tag used in the android workflow
    public static final String ARG_WORLD_SERIALIZED = "world_ser";
    private static final long serialVersionUID = 792709417041090031L;

    public static final String KEY_HAS_ERROR = "hasError";
    public static final String KEY_FLAT_WORLD_LAYERS = "FlatWorldLayers";
    public static final String KEY_GAME_TYPE = "GameType";
    public static final String KEY_GENERATOR = "Generator";
    public static final String KEY_INVENTORY_VERSION = "InventoryVersion";
    public static final String KEY_MINIMUM_VERSION = "MinimumCompatibleClientVersion";
    public static final String KEY_MINIMUM_VERSION_SHORT = "MinClientVersion";
    public static final String KEY_STORAGE_VERSION = "StorageVersion";
    public static final String KEY_LAST_VERSION = "lastOpenedWithVersion";
    public static final String KEY_LAST_VERSION_SHORT = "lastWithVersion";
    public static final String KEY_SHOW_COORDINATES = "showcoordinates";

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

    public Bundle getMapVersionData() {
        try {
            Bundle bundle = new Bundle();
            {
                Tag tag = level.getChildTagByKey(KEY_STORAGE_VERSION);
                int ver;
                if (!(tag instanceof IntTag)) ver = -1;
                else {
                    Integer intInt = ((IntTag) tag).getValue();
                    ver = intInt == null ? -1 : intInt;
                }
                bundle.putInt(KEY_STORAGE_VERSION, ver);
            }
            {
                Tag tag = level.getChildTagByKey(KEY_INVENTORY_VERSION);
                String ver;
                if (!(tag instanceof StringTag)) ver = null;
                else ver = ((StringTag) tag).getValue();
                bundle.putString(KEY_INVENTORY_VERSION, ver);
            }
            {
                Tag tag = level.getChildTagByKey(KEY_LAST_VERSION);
                ArrayList<Tag> ver;
                if (!(tag instanceof ListTag)) ver = null;
                else ver = ((ListTag) tag).getValue();
                StringBuilder sb = new StringBuilder();
                if (ver != null) for (Tag t : ver) {
                    int iver;
                    if (!(t instanceof IntTag)) iver = -1;
                    else iver = ((IntTag) t).getValue();
                    sb.append(iver).append('.');
                }
                bundle.putString(KEY_LAST_VERSION_SHORT, sb.toString());
            }
            {
                Tag tag = level.getChildTagByKey(KEY_MINIMUM_VERSION);
                ArrayList<Tag> ver;
                if (!(tag instanceof ListTag)) ver = null;
                else ver = ((ListTag) tag).getValue();
                StringBuilder sb = new StringBuilder();
                if (ver != null) for (Tag t : ver) {
                    int iver;
                    if (!(t instanceof IntTag)) iver = -1;
                    else iver = ((IntTag) t).getValue();
                    sb.append(iver).append('.');
                }
                bundle.putString(KEY_MINIMUM_VERSION_SHORT, sb.toString());
            }
            Iterator iterator = null;
            try {
                SparseIntArray versions = new SparseIntArray();
                //SparseIntArray dimensions = new SparseIntArray();
                iterator = getWorldData().db.iterator();
                int count = 0;
                for (iterator.seekToFirst(); iterator.isValid() && count < 800; iterator.next(), count++) {
                    byte[] key = iterator.getKey();
                    if ((key.length == 9 && key[8] == 0x76) || (key.length == 13 && key[12] == 0x76)) {
                        //if (key.length == 9) dimensions.put(0, dimensions.get(0) + 1);
                        //else {
                        //    int dimension = (key[8] & 0xff) | ((key[9] & 0xff) << 8) | ((key[10] & 0xff) << 16) | (key[11] << 24);
                        //    dimensions.put(dimension, dimensions.get(dimension) + 1);
                        //}
                        byte[] val = iterator.getValue();
                        versions.put(val[0], versions.get(val[0]) + 1);
                    }
                }
                iterator.close();
                StringBuilder sb = new StringBuilder();
                for (int i = 0, lim = Math.min(versions.size(), 8); i < lim; i++) {
                    sb.append(versions.keyAt(i)).append(':').append(versions.valueAt(i)).append(',');
                }
                bundle.putString("chunks", sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
                if (iterator != null) {
                    iterator.close();
                }
            }
            return bundle;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
