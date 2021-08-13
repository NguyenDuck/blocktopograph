package com.mithrilmania.blocktopograph.chunk.terrain;

import android.util.Pair;

import androidx.annotation.NonNull;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import com.mithrilmania.blocktopograph.BuildConfig;
import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.block.Block;
import com.mithrilmania.blocktopograph.block.BlockTemplate;
import com.mithrilmania.blocktopograph.block.BlockTemplates;
import com.mithrilmania.blocktopograph.block.BlockType;
import com.mithrilmania.blocktopograph.block.blockproperty.BlockProperty;
import com.mithrilmania.blocktopograph.chunk.ChunkTag;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.nbt.convert.NBTInputStream;
import com.mithrilmania.blocktopograph.nbt.convert.NBTOutputStream;
import com.mithrilmania.blocktopograph.nbt.tags.ByteTag;
import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.IntTag;
import com.mithrilmania.blocktopograph.nbt.tags.StringTag;
import com.mithrilmania.blocktopograph.util.LittleEndianOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class V1d2d13TerrainSubChunk extends TerrainSubChunk {

    private boolean mIsDualStorageSupported;

    // Masks used to extract BlockState bits of a certain oldBlock out of a int32, and vice-versa.
    private static final int[] msk = {0b1, 0b11, 0b111, 0b1111, 0b11111, 0b111111, 0b1111111,
            0b11111111,
            0b111111111, 0b1111111111, 0b11111111111,
            0b111111111111,
            0b1111111111111, 0b11111111111111, 0b11111111111111};
    // There could be multiple BlockStorage let's read the first two.
    private final BlockStorage[] mStorages;

    V1d2d13TerrainSubChunk() {
        mStorages = new BlockStorage[2];
        mIsDualStorageSupported = true;
        createEmptyBlockStorage(0);
    }

    V1d2d13TerrainSubChunk(@NonNull ByteBuffer raw) {

        raw.order(ByteOrder.LITTLE_ENDIAN);
        mStorages = new BlockStorage[2];

        // The first byte indicates version.
        switch (raw.get(0)) {
            // 1: Only one BlockStorage starting from the next byte.
            case 1:
                mIsDualStorageSupported = false;
                raw.position(1);
                try {
                    mStorages[0] = BlockStorage.loadAndMoveForward(raw);
                } catch (IOException e) {
                    if (BuildConfig.DEBUG) {
                        Log.d(this, e);
                    }
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
                    mStorages[0] = BlockStorage.loadAndMoveForward(raw);
                    if (count > 1) mStorages[1] = BlockStorage.loadAndMoveForward(raw);
                } catch (IOException e) {
                    if (BuildConfig.DEBUG) {
                        Log.d(this, e);
                    }
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
    @Override
    public BlockTemplate getBlockTemplate(int x, int y, int z, int layer) {
        if (mIsError) return BlockTemplates.getAirTemplate();
        BlockStorage storage = mStorages[layer];
        if (storage == null) return BlockTemplates.getAirTemplate();
        return storage.getBlock(x, y, z).second;
    }

    @NonNull
    @Override
    public Block getBlock(int x, int y, int z, int layer) {
        if (mIsError) throw new RuntimeException();
        BlockStorage storage = mStorages[layer];
        if (storage == null) return BlockTemplates.getAirTemplate().getBlock();
        return storage.getBlock(x, y, z).first;
    }

    @Override
    public void setBlock(int x, int y, int z, int layer, @NonNull Block block) {

        // Has error or not supported.
        if (mIsError || (layer > 0 && !mIsDualStorageSupported)) throw new RuntimeException();

        BlockStorage storage = mStorages[layer];
        // Main storage won't be null unless error.
        if (storage == null) storage = createEmptyBlockStorage(layer);

        // If space is enough.
        if (storage.setBlockIfSpace(x, y, z, block)) return;

        // Or we have to extend the whole storage.
        storage = BlockStorage.extend(storage);

        mStorages[layer] = storage;
        storage.setBlockIfSpace(x, y, z, block);
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
        BlockStorage storage = BlockStorage.createNew();
        mStorages[which] = storage;
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
            mStorages[0].write(leos);
            if (storageCount == 2) mStorages[1].write(leos);
        } else {
            leos.write(1);
            mStorages[0].write(leos);
        }
        leos.flush();

        byte[] arr = baos.toByteArray();
        worldData.writeChunkData(chunkX, chunkZ, ChunkTag.TERRAIN, dimension, (byte) which, true, arr);

    }

    private static class BlockStorage {

        public static final String PALETTE_KEY_ROOT = "";
        public static final String PALETTE_KEY_NAME = "name";
        public static final String PALETTE_KEY_STATES = "states";
        public static final String PALETTE_KEY_VERSION = "version";

        private final byte[] raw;

        // records is a view into raw
        private final IntBuffer records;

        private final List<Block> palette;

        private final List<BlockTemplate> renderPalette;

        private final int blockCodeLenth;

        private BlockStorage() {
            raw = new byte[512];
            ByteBuffer bbuff = ByteBuffer.wrap(raw);
            bbuff.order(ByteOrder.LITTLE_ENDIAN);
            records = bbuff.asIntBuffer();

            palette = new ArrayList<>(4);
            renderPalette = new ArrayList<>(4);
            var airTemplate = BlockTemplates.getAirTemplate();
            renderPalette.add(airTemplate);
            palette.add(airTemplate.getBlock());

            blockCodeLenth = 1;
        }

        // stateful! wrapping with static creation method
        private BlockStorage(@NonNull ByteBuffer buffer) throws IOException {

            //Read BlockState length.
            //this byte = (length << 2) | serializedType.
            blockCodeLenth = (buffer.get() & 0xff) >> 1;

            if (blockCodeLenth > 16) throw new IOException("mBlockLength > 16");

            //We use this much of bytes to store BlockStates.
            int bufsize = (4095 / (32 / blockCodeLenth) + 1) << 2;
            byte[] arr = new byte[bufsize];
            ByteBuffer byteBuffer = ByteBuffer.wrap(arr);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            records = byteBuffer.asIntBuffer();
            raw = arr;

            //No convenient way copy these stuff.
            byteBuffer.put(buffer.array(), buffer.position(), bufsize);
            buffer.position(buffer.position() + bufsize);

            //Palette items count.
            int psize = buffer.getInt();

//        if(psize>(1<<mMainBlockCodeLenth)){
//            throw new IOException("psize > most possible bound");
//        }

            //Construct the palette. Each item is a piece of nbt data.
            palette = new ArrayList<>(16);
            renderPalette = new ArrayList<>(16);

            //NBT reader requires a stream.
            var bais = new ByteArrayInputStream(buffer.array());

            // Skip for byte array would not fail.
            //noinspection ResultOfMethodCallIgnored
            bais.skip(buffer.position());

            //Wrap it.
            var nis = new NBTInputStream(bais, false);
            for (int i = 0; i < psize; i++)
                //Read a piece of nbt data, represented by a root CompoundTag.
                addToPalette(deserializeBlock((CompoundTag) nis.readTag()));

            //If one day we need to read more BlockStorage's, this line helps.
            buffer.position(buffer.position() + nis.getReadCount());
        }

        private BlockStorage(@NonNull BlockStorage old) {
            blockCodeLenth = old.blockCodeLenth + 1;
            palette = new ArrayList<>(old.palette);
            renderPalette = new ArrayList<>(old.renderPalette);
            int capa_new = 32 / blockCodeLenth;
            int capa_old = 32 / old.blockCodeLenth;
            int stick = 4095 / capa_new + 1;
            byte[] newRecs = new byte[stick << 2];
            ByteBuffer newBb = ByteBuffer.wrap(newRecs);
            newBb.order(ByteOrder.LITTLE_ENDIAN);
            records = newBb.asIntBuffer();
            raw = newRecs;
            for (int i = 0, hold = 0, hnew = 0, mold = 0, mnew = 0; i < 4096; i++) {
                int idold =
                        (old.records.get(hold) >> (mold * old.blockCodeLenth)) & msk[old.blockCodeLenth - 1];
                idold <<= mnew * blockCodeLenth;
                records.put(hnew, records.get(hnew) | idold);
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
        }

        public static BlockStorage createNew() {
            return new BlockStorage();
        }

        public static BlockStorage loadAndMoveForward(@NonNull ByteBuffer buffer) throws IOException {
            return new BlockStorage(buffer);
        }

        public static BlockStorage extend(@NonNull BlockStorage storage) {
            return new BlockStorage(storage);
        }

        private void addToPalette(Block block) {
            palette.add(block);
            renderPalette.add(BlockTemplates.getBest(block));
        }

        public boolean setBlockIfSpace(
                int x, int y, int z, @NonNull Block block) {
            int code = -1;

            // If in palette.
            for (int localId = 0, paletteSize = palette.size(); localId < paletteSize; localId++) {
                Block blockInPalette = palette.get(localId);
                if (block.equals(blockInPalette)) {
                    code = localId;
                    break;
                }
            }

            // Or not.
            if (code < 0) {
                // Reached size limit.
                int max = 1 << blockCodeLenth;
                int size = palette.size();
                if (size >= max) return false;

                addToPalette(block);
                code = size;
            }

            // The codeOffset'th BlockState is wanted.
            int codeOffset = getOffset(x, y, z);

            // How much BlockStates can one int32 hold?
            int intCapa = 32 / blockCodeLenth;

            // The int32 that holds the wanted BlockState.
            int whichInt = codeOffset / intCapa;
            int stick = records.get(whichInt);
            int shift = codeOffset % intCapa * blockCodeLenth;
            stick &= ~(msk[blockCodeLenth - 1] << shift);
            stick |= code << shift;
            records.put(whichInt, stick);

            return true;
        }

        public Pair<Block, BlockTemplate> getBlock(int x, int y, int z) {

            //The codeOffset'th BlockState is wanted.
            int codeOffset = getOffset(x, y, z);

            //How much BlockStates can one int32 hold?
            int intCapa = 32 / blockCodeLenth;

            //The int32 that holds the wanted BlockState.
            int stick = records.get(codeOffset / intCapa);

            //Get the BlockState. It's also the index in palette array.
            int ind = (stick >> (codeOffset % intCapa * blockCodeLenth)) & msk[blockCodeLenth - 1];

            //Transform the local BlockState into global id<<8|data structure.
            return new Pair<>(palette.get(ind), renderPalette.get(ind));
        }

        private void write(@NonNull LittleEndianOutputStream stream) throws IOException {

            // Code length.
            stream.write(blockCodeLenth << 1);

            // Int32s.
            stream.write(raw);

            // Palette size.
            int size = palette.size();
            stream.writeInt(size);

            // Palettes.
            NBTOutputStream nos = new NBTOutputStream(stream, false, true);

            for (int j = 0; j < size; j++)
                nos.writeTag(serializeBlock(palette.get(j)));
        }

        private static CompoundTag serializeBlock(@NonNull Block block) {
            return new CompoundTag(PALETTE_KEY_ROOT, Lists.newArrayList(
                    new StringTag(PALETTE_KEY_NAME, block.getName()),
                    new CompoundTag(PALETTE_KEY_STATES, new ArrayList<>(Streams.concat(Streams.zip(
                            Arrays.stream(block.getType().getKnownProperties()).map(BlockProperty::getName),
                            Arrays.stream(block.getKnownProperties()), Maps::immutableEntry).filter(Objects::nonNull),
                            block.getCustomProperties().entrySet().stream()).map(
                            (entry) -> {
                                var name = entry.getKey();
                                var val = entry.getValue();
                                if (val instanceof Byte) return new ByteTag(name, (Byte) val);
                                else if (val instanceof Integer)
                                    return new IntTag(name, (Integer) val);
                                else if (val instanceof String)
                                    return new StringTag(name, (String) val);
                                else
                                    throw new RuntimeException("block state with unsupported type");
                            }).collect(Collectors.toList()))),
                    new IntTag(PALETTE_KEY_VERSION, 2012)
            ));
        }

        private static Block deserializeBlock(@NonNull CompoundTag tag) {
            var name = ((StringTag) tag.getChildTagByKey(PALETTE_KEY_NAME)).getValue();
            var blockType = BlockType.get(name);
            var builder = (blockType == null ? new Block.Builder(name) : new Block.Builder(blockType));
            for (var state : ((CompoundTag) tag.getChildTagByKey(PALETTE_KEY_STATES)).getValue())
                builder.setProperty(state);
            Log.d(BlockStorage.class, "fuckfuckversion" + tag.getChildTagByKey(PALETTE_KEY_VERSION).getValue());
            return builder.build();
        }

    }
}
