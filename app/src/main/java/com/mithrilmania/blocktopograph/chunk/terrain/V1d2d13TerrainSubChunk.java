package com.mithrilmania.blocktopograph.chunk.terrain;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.ChunkTag;
import com.mithrilmania.blocktopograph.map.Block;
import com.mithrilmania.blocktopograph.map.BlockNameResolver;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.nbt.convert.NBTInputStream;
import com.mithrilmania.blocktopograph.nbt.convert.NBTOutputStream;
import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.ShortTag;
import com.mithrilmania.blocktopograph.nbt.tags.StringTag;
import com.mithrilmania.blocktopograph.nbt.tags.Tag;
import com.mithrilmania.blocktopograph.util.LittleEndianOutputStream;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public final class V1d2d13TerrainSubChunk extends TerrainSubChunk {

    public static final String PALETTE_KEY_NAME = "name";
    public static final String PALETTE_KEY_VAL = "val";
    public static final String PREFIX_MINECRAFT = "minecraft:";
    private boolean mIsDualStorageSupported;

    //There could be multiple BlockStorage let's read the first two.
    // No not anymore.
    private volatile BlockStorage[] mStorages;

    //Masks used to extract BlockState bits of a certain block out of a int32, and vice-versa.
    private static final int[] msk = {0b1, 0b11, 0b111, 0b1111, 0b11111, 0b111111, 0b1111111,
            0b11111111,
            0b111111111, 0b1111111111, 0b11111111111,
            0b111111111111,
            0b1111111111111, 0b11111111111111, 0b11111111111111};

    /*public*/ V1d2d13TerrainSubChunk(@NotNull ByteBuffer raw) {

        raw.order(ByteOrder.LITTLE_ENDIAN);
        mStorages = new BlockStorage[2];

        //The first byte indicates version.
        switch (raw.get(0)) {
            //1: Only one BlockStorage starting from the next byte.
            case 1:
                mIsDualStorageSupported = false;
                raw.position(1);
                try {
                    loadBlockStorage(raw, 0);
                } catch (IOException e) {
                    e.printStackTrace();
                    mIsError = true;
                }
                break;
            //8: One or more BlockStorage's, next byte is the count.
            case 8:
                mIsDualStorageSupported = true;
                raw.position(1);
                int count = raw.get();
                if (count < 1) {
                    mIsError = true;
                    return;
                }
                try {
                    loadBlockStorage(raw, 0);
                    if (count > 1) loadBlockStorage(raw, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                    mIsError = true;
                }
                break;
            default:
                mIsError = true;
                return;
        }
        mHasBlockLight = false;
        mHasSkyLight = false;
    }

    private static boolean setBlockRuntimeIdIfSpace(
            int x, int y, int z, @NotNull BlockStorage storage, int runtimeId) {
        int code = -1;

        // If in palette.
        List<Integer> palette = storage.palette;
        for (int localId = 0, paletteSize = palette.size(); localId < paletteSize; localId++) {
            Integer runtimeIdInPalette = palette.get(localId);
            if (runtimeIdInPalette != null && runtimeIdInPalette == runtimeId) {
                code = localId;
                break;
            }
        }

        // Or not.
        if (code < 0) {
            // Reached size limit.
            int max = 1 << storage.blockCodeLenth;
            int size = palette.size();
            if (size >= max) return false;

            palette.add(runtimeId);
            code = size;
        }

        //The codeOffset'th BlockState is wanted.
        int codeOffset = getOffset(x, y, z);

        //How much BlockStates can one int32 hold?
        int intCapa = 32 / storage.blockCodeLenth;

        //The int32 that holds the wanted BlockState.
        int whichInt = codeOffset / intCapa;
        int stick = storage.records.get(whichInt);
        int shift = codeOffset % intCapa * storage.blockCodeLenth;
        stick &= ~(msk[storage.blockCodeLenth - 1] << shift);
        stick |= code << shift;
        storage.records.put(whichInt, stick);

        return true;
    }

    private static void writeStorage(@NotNull BlockStorage storage, @NotNull LittleEndianOutputStream stream) throws IOException {

        // Code length.
        stream.write(storage.blockCodeLenth << 1);

        // Int32s.
        stream.write(storage.raw);

        // Palette size.
        List<Integer> palette = storage.palette;
        int size = palette.size();
        stream.writeInt(size);

        // Palettes.
        NBTOutputStream nos = new NBTOutputStream(stream, false, true);
        for (int j = 0; j < size; j++) {
            Integer runtimeId = palette.get(j);
            Block block = Block.getBlock(runtimeId);
            if (block == null) block = Block.getBlock(runtimeId & 0xffff00);
            if (block == null) block = Block.B_0_0_AIR;
            StringTag nameTag = new StringTag(PALETTE_KEY_NAME, PREFIX_MINECRAFT + block.str);
            ShortTag valTag = new ShortTag(PALETTE_KEY_VAL, (short) block.subId);
            ArrayList<Tag> pitem = new ArrayList<>(2);
            pitem.add(nameTag);
            pitem.add(valTag);
            nos.writeTag(new CompoundTag("", pitem));
        }
    }

    private void loadBlockStorage(ByteBuffer raw, int which) throws IOException {

        BlockStorage storage = new BlockStorage();
        mStorages[which] = storage;

        //Read BlockState length.
        //this byte = (length << 2) | serializedType.
        storage.blockCodeLenth = (raw.get() & 0xff) >> 1;

        if (storage.blockCodeLenth > 16) throw new IOException("mBlockLength > 16");

        //We use this much of bytes to store BlockStates.
        int bufsize = (4095 / (32 / storage.blockCodeLenth) + 1) << 2;
        byte[] arr = new byte[bufsize];
        ByteBuffer byteBuffer = ByteBuffer.wrap(arr);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        storage.records = byteBuffer.asIntBuffer();
        storage.raw = arr;

        //No convenient way copy these stuff.
        byteBuffer.put(raw.array(), raw.position(), bufsize);
        raw.position(raw.position() + bufsize);

        //Palette items count.
        int psize = raw.getInt();

//        if(psize>(1<<mMainBlockCodeLenth)){
//            throw new IOException("psize > most possible bound");
//        }

        //Construct the palette. Each item is a piece of nbt data.
        storage.palette = new ArrayList<>(16);

        //NBT reader requires a stream.
        ByteArrayInputStream bais = new ByteArrayInputStream(raw.array());

        // Skip for byte array would not fail.
        //noinspection ResultOfMethodCallIgnored
        bais.skip(raw.position());

        //Wrap it.
        NBTInputStream nis = new NBTInputStream(bais, false);
        for (int i = 0; i < psize; i++) {

            //Read a piece of nbt data, represented by a root CompoundTag.
            CompoundTag tag = (CompoundTag) nis.readTag();

            //Read `name` and `val` then resolve the `name` into numeric id.
            String name = ((StringTag) tag.getChildTagByKey(PALETTE_KEY_NAME)).getValue();
            int data = ((ShortTag) tag.getChildTagByKey(PALETTE_KEY_VAL)).getValue();
            storage.palette.add(BlockNameResolver.resolve(name) << 8 | data);
        }

        //If one day we need to read more BlockStorage's, this line helps.
        raw.position(raw.position() + nis.getReadCount());
    }

    @Override
    public int getBlockRuntimeId(int x, int y, int z, int layer) {

        if (mIsError) return 0;

        BlockStorage storage = mStorages[layer];
        if (storage == null) return 0;

        //The codeOffset'th BlockState is wanted.
        int codeOffset = getOffset(x, y, z);

        //How much BlockStates can one int32 hold?
        int intCapa = 32 / storage.blockCodeLenth;

        //The int32 that holds the wanted BlockState.
        int stick = storage.records.get(codeOffset / intCapa);

        //Get the BlockState. It's also the index in palette array.
        int ind = (stick >> (codeOffset % intCapa * storage.blockCodeLenth)) & msk[storage.blockCodeLenth - 1];

        //Transform the local BlockState into global id<<8|data structure.
        return storage.palette.get(ind);
    }

    @Override
    public void setBlockRuntimeId(int x, int y, int z, int layer, int runtimeId) {

        // Has error or not supported.
        if (mIsError || (layer > 0 && !mIsDualStorageSupported)) return;

        BlockStorage storage = mStorages[layer];
        // Main storage won't be null unless error.
        if (storage == null) storage = createSubBlockStorage();

        // If space is enough.
        if (setBlockRuntimeIdIfSpace(x, y, z, storage, runtimeId)) return;

        // Or we have to extend the whole storage.
        BlockStorage newStorage = new BlockStorage();
        newStorage.blockCodeLenth = storage.blockCodeLenth + 1;
        newStorage.palette = new ArrayList<>(storage.palette);
        int capa_new = 32 / newStorage.blockCodeLenth;
        int capa_old = 32 / storage.blockCodeLenth;
        int stick = 4095 / capa_new + 1;
        byte[] newRecs = new byte[stick << 2];
        ByteBuffer newBb = ByteBuffer.wrap(newRecs);
        newBb.order(ByteOrder.LITTLE_ENDIAN);
        newStorage.records = newBb.asIntBuffer();
        newStorage.raw = newRecs;

        for (int i = 0, hold = 0, hnew = 0, mold = 0, mnew = 0; i < 4096; i++) {
            int idold =
                    (storage.records.get(hold) >> (mold * storage.blockCodeLenth)) & msk[storage.blockCodeLenth - 1];
            idold <<= mnew * newStorage.blockCodeLenth;
            newStorage.records.put(hnew, newStorage.records.get(hnew) | idold);
            mold++;
            mnew++;
            if (mold == capa_old) {
                mold = 0;
                hold++;
            }
            if (mnew == capa_new) {
                mnew = 0;
                hnew++;
            }
        }
        mStorages[layer] = newStorage;
        setBlockRuntimeIdIfSpace(x, y, z, newStorage, runtimeId);
    }

    @Override
    public int getBlockLightValue(int x, int y, int z) {
        return 0;
    }

    @Override
    public int getSkyLightValue(int x, int y, int z) {
        return 0;
    }

    private BlockStorage createSubBlockStorage() {

        BlockStorage storage = new BlockStorage();
        mStorages[1] = storage;

        byte[] arr = new byte[512];
        ByteBuffer bbuff = ByteBuffer.wrap(arr);
        bbuff.order(ByteOrder.LITTLE_ENDIAN);
        storage.records = bbuff.asIntBuffer();
        storage.raw = arr;

        storage.palette = new ArrayList<>(4);
        storage.palette.add(0);

        storage.blockCodeLenth = 1;

        return storage;
    }

    @Override
    public void save(WorldData worldData, int chunkX, int chunkZ, Dimension dimension, int which) throws WorldData.WorldDBException, IOException {

        if (mIsError) return;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        LittleEndianOutputStream leos = new LittleEndianOutputStream(baos);

        int storageCount = mIsDualStorageSupported ? (mStorages[1] == null ? 1 : 2) : 1;

        if (mIsDualStorageSupported) {
            leos.write(8);
            leos.write(storageCount);
            writeStorage(mStorages[0], leos);
            if (storageCount == 2) writeStorage(mStorages[1], leos);
        } else {
            leos.write(1);
            writeStorage(mStorages[0], leos);
        }
        leos.flush();

        byte[] arr = baos.toByteArray();
        worldData.writeChunkData(chunkX, chunkZ, ChunkTag.TERRAIN, dimension, (byte) which, true, arr);

    }

    private static class BlockStorage {

        public byte[] raw;
        public IntBuffer records;
        public List<Integer> palette;
        public int blockCodeLenth;

    }
}
