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
        loadFromByteArray(chunk.worldData.getChunkData(chunk.x, chunk.z, dataType, this.chunk.dimension, (byte) 0, false));
    }

    public void loadFromByteArray(byte[] data) throws IOException {
        if (data != null && data.length > 0) this.tags = DataConverter.read(data);
    }

    public void write() throws WorldData.WorldDBException, IOException {
        if (this.tags == null) this.tags = new ArrayList<>();
        byte[] data = DataConverter.write(this.tags);
        this.chunk.worldData.writeChunkData(this.chunk.x, this.chunk.z, this.dataType, this.chunk.dimension, (byte) 0, false, data);
    }

    @Override
    public void createEmpty() {
        if(this.tags == null) this.tags = new ArrayList<>();
        this.tags.add(new IntTag("Placeholder", 42));
    }

}
