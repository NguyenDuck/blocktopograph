package com.mithrilmania.blocktopograph.chunk.terrain;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.map.BlockNameResolver;
import com.mithrilmania.blocktopograph.nbt.convert.NBTInputStream;
import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.ShortTag;
import com.mithrilmania.blocktopograph.nbt.tags.StringTag;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public final class V1d2d13TerrainSubChunk extends TerrainSubChunk {

    private boolean mIsDualStorageSupported;

    //There could be multiple BlockStorage but we can just display the main.
    // No not anymore.
    private volatile IntBuffer mMainStorage;
    private volatile IntBuffer mSubStorage;
    private volatile List<Integer> mMainPalette;
    private volatile List<Integer> mSubPalette;
    private volatile int mMainBlockTypes;
    private volatile int mSubBlockTypes;
    private volatile int mMainBlockCodeLenth;
    private volatile int mSubBlockCodeLenth;

    //Masks used to extract BlockState bits of a certain block out of a int32, and vice-versa.
    private static final int[] msk = {0b1, 0b11, 0b111, 0b1111, 0b11111, 0b111111, 0b1111111,
            0b11111111,
            0b111111111, 0b1111111111, 0b11111111111,
            0b111111111111,
            0b1111111111111, 0b11111111111111, 0b11111111111111};

    V1d2d13TerrainSubChunk(ByteBuffer raw) {

        raw.order(ByteOrder.LITTLE_ENDIAN);
        //The first byte indicates version.
        switch (raw.get(0)) {
            //1: Only one BlockStorage starting from the next byte.
            case 1:
                mIsDualStorageSupported = false;
                raw.position(1);
                break;
            //8: One or more BlockStorage's, next byte is the count. We only read the first.
            case 8:
                mIsDualStorageSupported = true;
                raw.position(2);
                break;
            default:
                mIsError = true;
                return;
        }
        mHasBlockLight = false;
        mHasSkyLight = false;

        //Load the BlockStorage.
        try {
            loadBlockStorage(raw);
        } catch (IOException e) {
            e.printStackTrace();
            mIsError = true;
        }
    }

    private void loadBlockStorage(ByteBuffer raw) throws IOException {

        //Read BlockState length.
        //this byte = (length << 2) | serializedType.
        mMainBlockCodeLenth = (raw.get() & 0xff) >> 1;

        if (mMainBlockCodeLenth > 16) throw new IOException("mBlockLength > 16");

        //We use this much of bytes to store BlockStates.
        int bufsize = (4095 / (32 / mMainBlockCodeLenth) + 1) << 2;
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufsize);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        mMainStorage = byteBuffer.asIntBuffer();

        //No convenient way copy these stuff.
        byteBuffer.put(raw.array(), raw.position(), bufsize);
        raw.position(raw.position() + bufsize);

        //Palette items count.
        int psize = raw.getInt();

//        if(psize>(1<<mMainBlockCodeLenth)){
//            throw new IOException("psize > most possible bound");
//        }

        //Construct the palette. Each item is a piece of nbt data.
        mMainPalette = new ArrayList<>(16);

        //NBT reader requires a stream.
        ByteArrayInputStream bais = new ByteArrayInputStream(raw.array());
        bais.skip(raw.position());

        //Wrap it.
        NBTInputStream nis = new NBTInputStream(bais, false);
        for (int i = 0; i < psize; i++) {

            //Read a piece of nbt data, represented by a root CompoundTag.
            CompoundTag tag = (CompoundTag) nis.readTag();

            //Read `name` and `val` then resolve the `name` into numeric id.
            String name = ((StringTag) tag.getChildTagByKey("name")).getValue();
            int data = ((ShortTag) tag.getChildTagByKey("val")).getValue();
            mMainPalette.add(BlockNameResolver.resolve(name) << 8 | data);
        }

        //If one day we need to read more BlockStorage's, this line helps.
        raw.position(raw.position() + nis.getReadCount());
    }

    @Override
    public int getBlockRuntimeId(int x, int y, int z) {
        if (mIsError) return 0;

        //The codeOffset'th BlockState is wanted.
        int codeOffset = getOffset(x, y, z);

        //How much BlockStates can one int32 hold?
        int intCapa = 32 / mMainBlockCodeLenth;

        //The int32 that holds the wanted BlockState.
        int stick = mMainStorage.get(codeOffset / intCapa);

        //Get the BlockState. It's also the index in palette array.
        int ind = (stick >> (codeOffset % intCapa * mMainBlockCodeLenth)) & msk[mMainBlockCodeLenth - 1];

        //Transform the local BlockState into global id<<8|data structure.
        return mMainPalette.get(ind);
    }

    @Override
    public int getBlockRuntimeId(int x, int y, int z, int layer) {
        return 0;
    }

    @Override
    public void setBlockRuntimeId(int x, int y, int z, int layer, int runtimeId) {

    }

    @Override
    public int getBlockLightValue(int x, int y, int z) {
        return 0;
    }

    @Override
    public int getSkyLightValue(int x, int y, int z) {
        return 0;
    }

    @Override
    public void save(WorldData worldData) throws WorldData.WorldDBException {

    }
}
