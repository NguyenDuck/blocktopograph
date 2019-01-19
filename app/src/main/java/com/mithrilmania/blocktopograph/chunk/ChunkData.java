package com.mithrilmania.blocktopograph.chunk;


import com.mithrilmania.blocktopograph.WorldData;

import java.io.IOException;
import java.lang.ref.WeakReference;

public abstract class ChunkData {

    public final WeakReference<Chunk> chunk;


    public ChunkData(Chunk chunk) {
        this.chunk = new WeakReference<>(chunk);
    }

    public abstract void createEmpty();

    public abstract void write() throws IOException, WorldData.WorldDBException;

}
