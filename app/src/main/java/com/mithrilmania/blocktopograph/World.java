package com.mithrilmania.blocktopograph;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseIntArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.litl.leveldb.Iterator;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.map.MarkerManager;
import com.mithrilmania.blocktopograph.nbt.convert.DataConverter;
import com.mithrilmania.blocktopograph.nbt.convert.LevelDataConverter;
import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;
import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.IntTag;
import com.mithrilmania.blocktopograph.nbt.tags.ListTag;
import com.mithrilmania.blocktopograph.nbt.tags.LongTag;
import com.mithrilmania.blocktopograph.nbt.tags.StringTag;
import com.mithrilmania.blocktopograph.nbt.tags.Tag;
import com.mithrilmania.blocktopograph.util.IoUtil;
import com.mithrilmania.blocktopograph.util.math.DimensionVector3;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

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

    public String mark;
    private final String worldName;
    public final File worldFolder;
    public final File levelFile;
    private CompoundTag level;
    private transient WorldData worldData;
    private transient MarkerManager markersManager;

    private boolean mHaveBackgroundJob;

    public World(File worldFolder, String mark, @NonNull Context context) throws WorldLoadException {

        if (!worldFolder.exists())
            throw new WorldLoadException("Error: '" + worldFolder.getPath() + "' does not exist!");

        this.mark = mark;

        this.worldFolder = worldFolder;

        // check for a custom world name
        File levelNameTxt = new File(this.worldFolder, "levelname.txt");
        this.levelFile = new File(this.worldFolder, "level.dat");
        if (levelNameTxt.exists())
            worldName = IoUtil.readTextFileFirstLine(levelNameTxt);// new way of naming worlds
        else if (levelFile.exists())
            worldName = this.worldFolder.getName();// legacy way of naming worlds
        else worldName = context.getString(R.string.world_name_broken);


    }

    public CompoundTag getLevel() {
        if (level == null)
            try {
                this.level = LevelDataConverter.read(levelFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return level;
    }

    public String getWorldDisplayName() {
        if (worldName == null) return null;
        //return worldname, without special color codes
        // (character prefixed by the section-sign character)
        return worldName.replaceAll("\u00A7.", "");
    }

    public long getWorldSeed() {
        if (this.level == null) return 0;

        LongTag seed = (LongTag) this.level.getChildTagByKey("RandomSeed");
        return seed == null ? 0 : seed.getValue();
    }

    @Nullable
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
            Log.e(this, e);
            return null;
        }
    }

    public void writeLevel(CompoundTag level) throws IOException {
        LevelDataConverter.write(level, this.levelFile);
        this.level = level;
    }

    @NonNull
    public DimensionVector3<Float> getMultiPlayerPos(String dbKey) throws Exception {
        try {
            WorldData wData = getWorldData();
            wData.openDB();
            byte[] data = wData.db.get(dbKey.getBytes(NBTConstants.CHARSET));
            if (data == null) throw new Exception("no data!");
            final CompoundTag player = (CompoundTag) DataConverter.read(data).get(0);

            ListTag posVec = (ListTag) player.getChildTagByKey("Pos");
            if (posVec == null || posVec.getValue() == null)
                throw new Exception("No \"Pos\" specified");
            if (posVec.getValue().size() != 3)
                throw new Exception("\"Pos\" value is invalid. value: " + posVec.getValue().toString());

            IntTag dimensionId = (IntTag) player.getChildTagByKey("DimensionId");
            if (dimensionId == null || dimensionId.getValue() == null)
                throw new Exception("No \"DimensionId\" specified");
            Dimension dimension = Dimension.getDimension(dimensionId.getValue());
            if (dimension == null) dimension = Dimension.OVERWORLD;

            return new DimensionVector3<>(
                    (float) posVec.getValue().get(0).getValue(),
                    (float) posVec.getValue().get(1).getValue(),
                    (float) posVec.getValue().get(2).getValue(),
                    dimension);

        } catch (Exception e) {
            Log.d(this, e);
            Exception e2 = new Exception("Could not find " + dbKey);
            e2.setStackTrace(e.getStackTrace());
            throw e2;
        }
    }

    @Nullable
    public DimensionVector3<Float> getPlayerPos() {
        try {
            WorldData wData = getWorldData();
            wData.openDB();
            byte[] data = wData.db.get(World.SpecialDBEntryType.LOCAL_PLAYER.keyBytes);

            final CompoundTag player = data != null
                    ? (CompoundTag) DataConverter.read(data).get(0)
                    : (CompoundTag) getLevel().getChildTagByKey("Player");

            if (player == null) {
                Log.d(this, "No local player. A server world?");
                return null;
            }

            ListTag posVec = (ListTag) player.getChildTagByKey("Pos");
            IntTag dimensionId = (IntTag) player.getChildTagByKey("DimensionId");
            Dimension dimension = Dimension.getDimension(dimensionId.getValue());
            if (dimension == null) dimension = Dimension.OVERWORLD;

            return new DimensionVector3<>(
                    (float) posVec.getValue().get(0).getValue(),
                    (float) posVec.getValue().get(1).getValue(),
                    (float) posVec.getValue().get(2).getValue(),
                    dimension);
        } catch (Exception e) {
            Log.d(this, e);
            return null;
        }
    }

    public DimensionVector3<Integer> getSpawnPos() throws Exception {
        try {
            CompoundTag level = getLevel();
            int spawnX = ((IntTag) level.getChildTagByKey("SpawnX")).getValue();
            int spawnY = ((IntTag) level.getChildTagByKey("SpawnY")).getValue();
            int spawnZ = ((IntTag) level.getChildTagByKey("SpawnZ")).getValue();
            if (spawnY >= 256) try {
                Chunk chunk = getWorldData().getChunk(spawnX >> 4, spawnZ >> 4, Dimension.OVERWORLD);
                if (!chunk.isError())
                    spawnY = chunk.getHeightMapValue(spawnX % 16, spawnZ % 16) + 1;
            } catch (Exception ignored) {
            }
            return new DimensionVector3<>(spawnX, spawnY, spawnZ, Dimension.OVERWORLD);
        } catch (Exception e) {
            Log.d(this, e);
            throw new Exception("Could not find spawn");
        }
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

    @NonNull
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
        if (mHaveBackgroundJob)
            Log.d(this, "User is doing background job with the app really in background!");
        else
            closeDown();
    }

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
                Log.d(this, "key: " + new String(key) + " key in Hex: " + WorldData.bytesToHex(key, 0, key.length) + " size: " + value.length);

            }

            it.close();

        } catch (WorldData.WorldDBException e) {
            e.printStackTrace();
        }
    }

    public void setHaveBackgroundJob(boolean haveBackgroundJob) {
        mHaveBackgroundJob = haveBackgroundJob;
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
