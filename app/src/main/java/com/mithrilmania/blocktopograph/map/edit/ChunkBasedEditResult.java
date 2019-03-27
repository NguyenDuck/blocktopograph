package com.mithrilmania.blocktopograph.map.edit;

import com.mithrilmania.blocktopograph.map.Dimension;

/**
 * Used to publish result for every chunks in chunk-cased edit.
 */
public class ChunkBasedEditResult {

    public int x;

    public int z;

    public Dimension dimension;

    public EditResultCode resultCode;

    public ChunkBasedEditResult(int x, int z, Dimension dimension, EditResultCode resultCode) {
        this.x = x;
        this.z = z;
        this.dimension = dimension;
        this.resultCode = resultCode;
    }

}
