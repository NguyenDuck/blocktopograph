package com.mithrilmania.blocktopograph.map.edit;

import android.graphics.Rect;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.map.Dimension;

import org.jetbrains.annotations.NotNull;

public class RectEditTarget extends EditTarget {

    @NotNull
    private final Rect mArea;

    private final int yLowest;

    private final int yHighest;

    @NotNull
    private final Dimension dimension;

    public RectEditTarget(@NotNull WorldData worldData, @NotNull Rect area, @NotNull Dimension dimension) {
        super(true, worldData);
        mArea = new Rect(area);
        yLowest = 0;
        yHighest = 255;
        this.dimension = dimension;
    }

    @Override
    public void forEachXyzd(RandomAccessEdit edit) {

        int exceptionCount = 0;

        int chunkMinX = mArea.left >> 4;
        int chunkMaxX = mArea.right >> 4;
        int chunkMinZ = mArea.top >> 4;
        int chunkMaxZ = mArea.bottom >> 4;

        // Cache should not be used till end.

        for (int chunkX = chunkMinX; chunkX <= chunkMaxX; chunkX++) {
            int innerMinX = (chunkX == chunkMinX) ? (mArea.left & 0xf) : 0;
            int innerMaxX = (chunkX == chunkMaxX) ? (mArea.right & 0xf) : 15;

            for (int chunkZ = chunkMinZ; chunkZ <= chunkMaxZ; chunkZ++) {
                int innerMinZ = (chunkZ == chunkMinZ) ? (mArea.top & 0xf) : 0;
                int innerMaxZ = (chunkZ == chunkMaxZ) ? (mArea.bottom & 0xf) : 15;

                Chunk chunk = mWorldData.getChunkStreaming(chunkX, chunkZ, dimension);
                boolean supportHightMap = chunk.supportsHeightMap();

                for (int innerX = innerMinX; innerX <= innerMaxX; innerX++) {
                    for (int innerZ = innerMinZ; innerZ <= innerMaxZ; innerZ++) {

                        int h = yHighest;//supportHightMap ?
                        //Math.min(yHighest, chunk.getHeightMapValue(innerX, innerZ) - 1)
                        //: yHighest;
                        for (int y = yLowest; y <= h; y++)
                            edit.edit(chunk, innerX, y, innerZ);
                    }// End for innerZ
                }// End for innerX

                try {
                    chunk.save();
                } catch (Exception e) {
                    if (exceptionCount < 5) {
                        Log.d(this, e);
                        exceptionCount++;
                    }
                }
            }// End for ChunkZ
        }// End for ChunkX

        mWorldData.resetCache();
    }

    @Override
    public void forEachChunk(ChunkBasedEdit edit) {
        //
    }
}
