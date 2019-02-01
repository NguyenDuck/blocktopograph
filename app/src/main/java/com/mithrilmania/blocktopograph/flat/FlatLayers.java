package com.mithrilmania.blocktopograph.flat;

import com.mithrilmania.blocktopograph.Log;

import java.util.List;

public final class FlatLayers {

    private int biomeId;
    private int encodingVersion;
    private final List<Layer> mLayers;

    private FlatLayers(List<Layer> mLayers) {
        this.mLayers = mLayers;
        biomeId = 1;
        encodingVersion = 4;
        //biome_id
        //block_layers
        //block_name
        //block_data
        //count
        //encoding_version
    }

    public List<Layer> getLayers() {
        return mLayers;
    }

    public void clear() {
        mLayers.clear();
    }

    public int getBiomeId() {
        return biomeId;
    }

    public void setBiomeId(int biomeId) {
        this.biomeId = biomeId;
    }

    public int getEncodingVersion() {
        return encodingVersion;
    }

    public void setEncodingVersion(int encodingVersion) {
        this.encodingVersion = encodingVersion;
    }
}
