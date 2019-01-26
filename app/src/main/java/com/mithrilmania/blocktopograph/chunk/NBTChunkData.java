package com.mithrilmania.blocktopograph.chunk;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.nbt.convert.DataConverter;
import com.mithrilmania.blocktopograph.nbt.tags.IntTag;
import com.mithrilmania.blocktopograph.nbt.tags.Tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NBTChunkData extends ChunkData {

    public List<Tag> tags = new ArrayList<>();

    public final ChunkTag dataType;

    public NBTChunkData(Chunk chunk, ChunkTag dataType) {
        super(chunk);
        this.dataType = dataType;
    }

    public void load() throws WorldData.WorldDBLoadException, WorldData.WorldDBException, IOException {
        Chunk chunk = this.chunk.get();
        loadFromByteArray(chunk.getWorldData().getChunkData(chunk.mChunkX, chunk.mChunkZ, dataType, chunk.mDimension, (byte) 0, false));
    }

    public void loadFromByteArray(byte[] data) throws IOException {
        if (data != null && data.length > 0) this.tags = DataConverter.read(data);
    }

    public void write() throws WorldData.WorldDBException, IOException {
        if (this.tags == null) this.tags = new ArrayList<>();
        byte[] data = DataConverter.write(this.tags);
        Chunk chunk = this.chunk.get();
        chunk.getWorldData().writeChunkData(chunk.mChunkX, chunk.mChunkZ, this.dataType, chunk.mDimension, (byte) 0, false, data);
    }

    @Override
    public void createEmpty() {
        if (this.tags == null) this.tags = new ArrayList<>();
        this.tags.add(new IntTag("Placeholder", 42));
    }

}
