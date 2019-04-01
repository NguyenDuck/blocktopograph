package com.mithrilmania.blocktopograph.map.edit;

import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.Chunk;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public abstract class EditTarget {

    protected int mMaxError = 10;

    protected final boolean mIsChunkAware;
    @NotNull
    protected final WorldData mWorldData;

    protected EditTarget(boolean isChunkAware, @NotNull WorldData worldData) {
        mIsChunkAware = isChunkAware;
        mWorldData = worldData;
    }

    public abstract EditResultCode forEachXyz(RandomAccessEdit edit);

    public abstract EditResultCode forEachXz(RandomAccessEdit edit);

    public abstract EditResultCode forEachChunk(ChunkBasedEdit edit);

    public final void setMaxError(int maxError) {
        mMaxError = maxError;
    }

    @Contract(pure = true)
    public final boolean isChunkAware() {
        return mIsChunkAware;
    }

    public interface RandomAccessEdit {

        int edit(Chunk chunk, int x, int y, int z);

    }

    public interface ChunkBasedEdit {

        int edit(Chunk chunk, int fromX, int toX, int fromY, int toY, int fromZ, int toZ);

    }

}
