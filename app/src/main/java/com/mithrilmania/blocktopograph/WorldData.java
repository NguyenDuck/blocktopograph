package com.mithrilmania.blocktopograph;

import android.annotation.SuppressLint;

import com.mithrilmania.blocktopograph.chunk.ChunkTag;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.litl.leveldb.Iterator;
import com.litl.leveldb.DB;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper around level.dat world spec en levelDB database.
 */
public class WorldData {

    public static class WorldDataLoadException extends Exception {
        private static final long serialVersionUID = 659185044124115547L;

        public WorldDataLoadException(String msg) {
            super(msg);
        }
    }

    public static class WorldDBException extends Exception {
        private static final long serialVersionUID = -3299282170140961220L;

        public WorldDBException(String msg) {
            super(msg);
        }
    }

    public static class WorldDBLoadException extends Exception {
        private static final long serialVersionUID = 4412238820886423076L;

        public WorldDBLoadException(String msg) {
            super(msg);
        }
    }

    ///Meow
    private boolean isMeow;

    private World world;

    public DB db;

    public WorldData(World world, boolean isMeow) {
        this.world = world;

        //Meow
        this.isMeow = isMeow;
        android.util.Log.e("233", "isMeow=" + isMeow);
    }

    //load db when needed (does not load it!)
    @SuppressLint({"SetWorldReadable", "SetWorldWritable"})
    public void load() throws WorldDataLoadException {

        if (db != null) return;

        File dbFile = new File(this.world.worldFolder, "db");
        if (!dbFile.canRead()) {
            if (!dbFile.setReadable(true, false))
                throw new WorldDataLoadException("World-db folder is not readable! World-db folder: " + dbFile.getAbsolutePath());
        }
        if (!dbFile.canWrite()) {
            if (!dbFile.setWritable(true, false))
                throw new WorldDataLoadException("World-db folder is not writable! World-db folder: " + dbFile.getAbsolutePath());
        }

        Log.d("WorldFolder: " + this.world.worldFolder.getAbsolutePath());
        Log.d("WorldFolder permissions: read: " + dbFile.canRead() + " write: " + dbFile.canWrite());

        if (dbFile.listFiles() == null)
            throw new WorldDataLoadException("Failed loading world-db: cannot list files in worldfolder");

        for (File dbEntry : dbFile.listFiles()) {
            Log.d("File in db: " + dbEntry.getAbsolutePath());
        }
        this.db = new DB(dbFile);


    }

    //open db to make it available for this app
    public void openDB() throws WorldDBException {
        if (this.db == null)
            throw new WorldDBException("DB is null!!! (db is not loaded probably)");

        if (this.db.isClosed()) {
            try {
                this.db.open();
            } catch (Exception e) {

                throw new WorldDBException("DB could not be opened! " + e.getMessage());
            }
        }

    }

    //another method for debugging, makes it easy to print a readable byte array
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes, int start, int end) {
        char[] hexChars = new char[(end - start) * 2];
        for (int j = start; j < end; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[(j - start) * 2] = hexArray[v >>> 4];
            hexChars[(j - start) * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    //close db to make it available for other apps (Minecraft itself!)
    public void closeDB() throws WorldDBException {
        if (this.db == null)
            throw new WorldDBException("DB is null!!! (db is not loaded probably)");

        try {
            this.db.close();
        } catch (Exception e) {
            //db was already closed (probably)
            e.printStackTrace();
        }
    }

    /**
     * WARNING: DELETES WORLD !!!
     */
    public void destroy() throws WorldDBException {
        if (this.db == null)
            throw new WorldDBException("DB is null!!! (db is not loaded probably)");

        this.db.close();
        this.db.destroy();
        this.db = null;
    }

    public byte[] getChunkData(int x, int z, ChunkTag type, Dimension dimension, byte subChunk, boolean asSubChunk) throws WorldDBException, WorldDBLoadException {

        //ensure that the db is opened
        this.openDB();

        byte[] chunkKey = getChunkDataKey(x, z, type, dimension, subChunk, asSubChunk);
        //Log.d("Getting cX: "+x+" cZ: "+z+ " with key: "+bytesToHex(chunkKey, 0, chunkKey.length));
        return db.get(chunkKey);
    }

    public void writeChunkData(int x, int z, ChunkTag type, Dimension dimension, byte subChunk, boolean asSubChunk, byte[] chunkData) throws WorldDBException {
        //ensure that the db is opened
        this.openDB();

        db.put(getChunkDataKey(x, z, type, dimension, subChunk, asSubChunk), chunkData);
    }

    public void removeChunkData(int x, int z, ChunkTag type, Dimension dimension, byte subChunk, boolean asSubChunk) throws WorldDBException {
        //ensure that the db is opened
        this.openDB();

        db.delete(getChunkDataKey(x, z, type, dimension, subChunk, asSubChunk));
    }

    public String[] getPlayers() {
        List<String> players = getDBKeysStartingWith("player_");
        return players.toArray(new String[players.size()]);
    }

    public List<String> getDBKeysStartingWith(String startWith) {
        Iterator it = db.iterator();

        ArrayList<String> items = new ArrayList<>();
        for (it.seekToFirst(); it.isValid(); it.next()) {
            byte[] key = it.getKey();
            if (key == null) continue;
            String keyStr = new String(key);
            if (keyStr.startsWith(startWith)) items.add(keyStr);
        }
        it.close();

        return items;
    }

    public static byte[] getChunkDataKey(int x, int z, ChunkTag type, Dimension dimension, byte subChunk, boolean asSubChunk) {
        if (dimension == Dimension.OVERWORLD) {
            byte[] key = new byte[asSubChunk ? 10 : 9];
            System.arraycopy(getReversedBytes(x), 0, key, 0, 4);
            System.arraycopy(getReversedBytes(z), 0, key, 4, 4);
            key[8] = type.dataID;
            if (asSubChunk) key[9] = subChunk;
            return key;
        } else {
            byte[] key = new byte[asSubChunk ? 14 : 13];
            System.arraycopy(getReversedBytes(x), 0, key, 0, 4);
            System.arraycopy(getReversedBytes(z), 0, key, 4, 4);
            System.arraycopy(getReversedBytes(dimension.id), 0, key, 8, 4);
            key[12] = type.dataID;
            if (asSubChunk) key[13] = subChunk;
            return key;
        }
    }

    public static byte[] getReversedBytes(int i) {
        return new byte[]{
                (byte) i,
                (byte) (i >> 8),
                (byte) (i >> 16),
                (byte) (i >> 24)
        };
    }

}
