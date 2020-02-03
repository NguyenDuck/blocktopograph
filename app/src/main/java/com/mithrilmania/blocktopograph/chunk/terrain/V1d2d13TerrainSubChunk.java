package com.mithrilmania.blocktopograph.chunk.terrain;

import androidx.annotation.NonNull;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.block.Block;
import com.mithrilmania.blocktopograph.block.BlockRegistry;
import com.mithrilmania.blocktopograph.block.KnownBlockRepr;
import com.mithrilmania.blocktopograph.chunk.ChunkTag;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.nbt.convert.NBTInputStream;
import com.mithrilmania.blocktopograph.nbt.convert.NBTOutputStream;
import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.IntTag;
import com.mithrilmania.blocktopograph.nbt.tags.ShortTag;
import com.mithrilmania.blocktopograph.nbt.tags.StringTag;
import com.mithrilmania.blocktopograph.nbt.tags.Tag;
import com.mithrilmania.blocktopograph.util.LittleEndianOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public final class V1d2d13TerrainSubChunk extends TerrainSubChunk {

    private static final String PALETTE_KEY_NAME = "name";
    private static final String PALETTE_KEY_VAL = "val";
    private static final String PREFIX_MINECRAFT = "minecraft:";
    private boolean mIsDualStorageSupported;

    // Masks used to extract BlockState bits of a certain block out of a int32, and vice-versa.
    private static final int[] msk = {0b1, 0b11, 0b111, 0b1111, 0b11111, 0b111111, 0b1111111,
            0b11111111,
            0b111111111, 0b1111111111, 0b11111111111,
            0b111111111111,
            0b1111111111111, 0b11111111111111, 0b11111111111111};
    // There could be multiple BlockStorage let's read the first two.
    private volatile BlockStorage[] mStorages;

    V1d2d13TerrainSubChunk(@NonNull BlockRegistry blockRegistry) {
        super(blockRegistry);
        mStorages = new BlockStorage[2];
        mIsDualStorageSupported = false;
        createEmptyBlockStorage(0);
    }

    V1d2d13TerrainSubChunk(@NonNull ByteBuffer raw, @NonNull BlockRegistry blockRegistry) {
        super(blockRegistry);

        raw.order(ByteOrder.LITTLE_ENDIAN);
        mStorages = new BlockStorage[2];

        // The first byte indicates version.
        switch (raw.get(0)) {
            // 1: Only one BlockStorage starting from the next byte.
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
            // 8: One or more BlockStorage's, next byte is the count.
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

    @NonNull
    private static BlockStorage.BlockRecord createNewBlockRecord(@NonNull Block block) {
        BlockStorage.BlockRecord blockRecord = new BlockStorage.BlockRecord();
        blockRecord.blockResolved = block;
        StringTag nameTag = new StringTag(PALETTE_KEY_NAME, block.getBlockType());
        ArrayList<Tag> pitem = new ArrayList<>(2);
        pitem.add(nameTag);
        pitem.add(block.getStates());
        pitem.add(new IntTag("version", block.getVersion()));
        blockRecord.tag = new CompoundTag("", pitem);
        return blockRecord;
    }

    private boolean setBlockIfSpace(
            int x, int y, int z, @NonNull BlockStorage storage, @NonNull Block block) {
        int code = -1;

        // If in palette.
        List<BlockStorage.BlockRecord> palette = storage.palette;
        for (int localId = 0, paletteSize = palette.size(); localId < paletteSize; localId++) {
            Block blockInPalette = palette.get(localId).blockResolved;
            if (block.equals(blockInPalette)) {
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

            BlockRegistry blockRegistry = getBlockRegistry();
            if (blockRegistry == null) return true;

            palette.add(createNewBlockRecord(block));
            code = size;
        }

        // The codeOffset'th BlockState is wanted.
        int codeOffset = getOffset(x, y, z);

        // How much BlockStates can one int32 hold?
        int intCapa = 32 / storage.blockCodeLenth;

        // The int32 that holds the wanted BlockState.
        int whichInt = codeOffset / intCapa;
        int stick = storage.records.get(whichInt);
        int shift = codeOffset % intCapa * storage.blockCodeLenth;
        stick &= ~(msk[storage.blockCodeLenth - 1] << shift);
        stick |= code << shift;
        storage.records.put(whichInt, stick);

        return true;
    }

    private void writeStorage(@NonNull BlockStorage storage, @NonNull LittleEndianOutputStream stream) throws IOException {

        // Code length.
        stream.write(storage.blockCodeLenth << 1);

        // Int32s.
        stream.write(storage.raw);

        // Palette size.
        List<BlockStorage.BlockRecord> palette = storage.palette;
        int size = palette.size();
        stream.writeInt(size);

        // Palettes.
        NBTOutputStream nos = new NBTOutputStream(stream, false, true);

        BlockRegistry blockRegistry = getBlockRegistry();
        if (blockRegistry == null) return;
        for (int j = 0; j < size; j++)
            nos.writeTag(palette.get(j).tag);
    }

//    public CompoundTag[] tempGetPalettes(int baseX, int y, int z) {
//        return new CompoundTag[]{
//                tempGetPaletteItemOfPosition(baseX, y, z),
//                tempGetPaletteItemOfPosition(baseX + 8, y, z)
//        };
//    }
//
//    private CompoundTag tempGetPaletteItemOfPosition(int x, int y, int z) {
//        BlockStorage storage = mStorages[0];
//
//        //The codeOffset'th BlockState is wanted.
//        int codeOffset = getOffset(x, y, z);
//
//        //How much BlockStates can one int32 hold?
//        int intCapa = 32 / storage.blockCodeLenth;
//
//        //The int32 that holds the wanted BlockState.
//        int stick = storage.records.get(codeOffset / intCapa);
//
//        //Get the BlockState. It's also the index in palette array.
//        int ind = (stick >> (codeOffset % intCapa * storage.blockCodeLenth)) & msk[storage.blockCodeLenth - 1];
//
//        //Transform the local BlockState into global id<<8|data structure.
//        return storage.palette.get(ind).tag;
//    }

    private void loadBlockStorage(@NonNull ByteBuffer raw, int which) throws IOException {

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
        BlockRegistry blockRegistry = getBlockRegistry();
        if (blockRegistry == null) return;
        for (int i = 0; i < psize; i++) {

            //Read a piece of nbt data, represented by a root CompoundTag.
            CompoundTag tag = (CompoundTag) nis.readTag();
            BlockStorage.BlockRecord record = new BlockStorage.BlockRecord();
            record.tag = tag;

            //Read `name` and `val` then resolve the `name` into numeric id.
            String name = ((StringTag) tag.getChildTagByKey(PALETTE_KEY_NAME)).getValue();
            Tag statesTag = tag.getChildTagByKey("states");
            if (statesTag != null) {
                Tag verTag = tag.getChildTagByKey("version");
                record.blockResolved = blockRegistry.createBlock(name, (CompoundTag) statesTag,
                        verTag == null ? 2 : ((IntTag) verTag).getValue());
            } else {
                Tag valTag = tag.getChildTagByKey(PALETTE_KEY_VAL);
                KnownBlockRepr repr = KnownBlockRepr.getBlockNew(name,
                        valTag instanceof ShortTag ? ((ShortTag) valTag).getValue() : 0);
                if (repr == null) repr = KnownBlockRepr.guessBlockNew(name);
                record.blockResolved = blockRegistry.createBlock(repr);
            }
            storage.palette.add(record);
        }

        //If one day we need to read more BlockStorage's, this line helps.
        raw.position(raw.position() + nis.getReadCount());
    }

    @NonNull
    @Override
    public Block getBlock(int x, int y, int z, int layer) {

        if (mIsError) return getAir();

        BlockStorage storage = mStorages[layer];
        if (storage == null) return getAir();

        //The codeOffset'th BlockState is wanted.
        int codeOffset = getOffset(x, y, z);

        //How much BlockStates can one int32 hold?
        int intCapa = 32 / storage.blockCodeLenth;

        //The int32 that holds the wanted BlockState.
        int stick = storage.records.get(codeOffset / intCapa);

        //Get the BlockState. It's also the index in palette array.
        int ind = (stick >> (codeOffset % intCapa * storage.blockCodeLenth)) & msk[storage.blockCodeLenth - 1];

        //Transform the local BlockState into global id<<8|data structure.
        return storage.palette.get(ind).blockResolved;
    }

    @Override
    public void setBlock(int x, int y, int z, int layer, @NonNull Block block) {

        // Has error or not supported.
        if (mIsError || (layer > 0 && !mIsDualStorageSupported)) return;

        BlockStorage storage = mStorages[layer];
        // Main storage won't be null unless error.
        if (storage == null) storage = createEmptyBlockStorage(1);

        // If space is enough.
        if (setBlockIfSpace(x, y, z, storage, block)) return;

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
        setBlockIfSpace(x, y, z, newStorage, block);
    }

    @Override
    public int getBlockLightValue(int x, int y, int z) {
        return 0;
    }

    @Override
    public int getSkyLightValue(int x, int y, int z) {
        return 0;
    }

    private BlockStorage createEmptyBlockStorage(int which) {

        BlockStorage storage = new BlockStorage();
        mStorages[which] = storage;

        byte[] arr = new byte[512];
        ByteBuffer bbuff = ByteBuffer.wrap(arr);
        bbuff.order(ByteOrder.LITTLE_ENDIAN);
        storage.records = bbuff.asIntBuffer();
        storage.raw = arr;

        storage.palette = new ArrayList<>(4);
        storage.palette.add(createNewBlockRecord(getAir()));

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

        byte[] raw;

        IntBuffer records;

        List<BlockRecord> palette;

        int blockCodeLenth;

        static class BlockRecord {

            CompoundTag tag;
            Block blockResolved;

        }

    }
}
