package com.mithrilmania.blocktopograph.map.edit;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.chunk.Chunk;

public class DchunkEdit implements EditTarget.ChunkBasedEdit {

    private static final int MAX_EXCEPTION = 5;

    private int exceptionCount;

    public DchunkEdit() {
        exceptionCount = 0;
    }

    @Override
    public int edit(Chunk chunk, int fromX, int toX, int fromY, int toY, int fromZ, int toZ) {
        try {
            chunk.deleteThis();
        } catch (Exception e) {
            if (exceptionCount < MAX_EXCEPTION) {
                Log.d(this, e);
                exceptionCount++;
            }
            return -1;
        }
        return 0;
    }
}
