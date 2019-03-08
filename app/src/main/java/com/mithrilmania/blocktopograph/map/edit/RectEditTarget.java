package com.mithrilmania.blocktopograph.map.edit;

import android.graphics.Rect;

import com.mithrilmania.blocktopograph.WorldData;
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
        for (int x = mArea.left; x <= mArea.right; x++) {
            int chunkX = x >> 4;
            for (int z = mArea.top; z <= mArea.bottom; z++) {
                int chunkZ = z >> 4;
                for (int y = yLowest; y <= yHighest; y++)
                    edit.edit(mWorldData.getChunk(chunkX, chunkZ, dimension),
                            x & 0xf, y, z & 0xf);
            }
        }
    }

    @Override
    public void forEachChunk(ChunkBasedEdit edit) {
        //
    }
}
