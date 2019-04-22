package com.mithrilmania.blocktopograph.flat;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.map.KnownBlock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class FlatLayers {

    private static final String KEY_BIOME_ID = "biome_id";
    private static final String KEY_BLOCK_LAYERS = "block_layers";
    private static final String KEY_BLOCK_NAME = "block_name";
    private static final String KEY_BLOCK_DATA = "block_data";
    private static final String KEY_COUNT = "count";
    private static final String KEY_VERSION = "encoding_version";
    private static final String KEY_STRUCTURE_OPS = "structure_options";

    private boolean hasStructureOps;
    private int biomeId;
    private int encodingVersion;
    private Layer[] mLayers;

    @Nullable
    public static FlatLayers parse(String json) {
        try {
            JSONObject root = new JSONObject(json);
            FlatLayers layers = new FlatLayers();
            layers.biomeId = root.getInt(KEY_BIOME_ID);
            layers.encodingVersion = root.getInt(KEY_VERSION);
            JSONArray jlayers = root.getJSONArray(KEY_BLOCK_LAYERS);
            Layer[] alayers = new Layer[jlayers.length()];
            for (int i = 0; i < alayers.length; i++) {
                JSONObject jlayer = jlayers.getJSONObject(i);
                String name = jlayer.getString(KEY_BLOCK_NAME);
                int id = KnownBlock.resolve(name);
                int data = jlayer.getInt(KEY_BLOCK_DATA);
                int count = jlayer.getInt(KEY_COUNT);
                KnownBlock block = KnownBlock.getBestBlock(id, data);
                alayers[i] = new Layer(block, count);
            }
            layers.hasStructureOps = root.has(KEY_STRUCTURE_OPS);
            return layers;
        } catch (JSONException e) {
            Log.d(FlatLayers.class, e);
        }
        return null;
    }

    @NonNull
    public static FlatLayers createNew(int biomeId, Layer[] layers) {
        FlatLayers ret = new FlatLayers();
        ret.biomeId = biomeId;
        ret.encodingVersion = 4;
        ret.mLayers = layers;
        return ret;
    }

    private FlatLayers() {
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

    public Layer[] getmLayers() {
        return mLayers;
    }

    public void setmLayers(Layer[] mLayers) {
        this.mLayers = mLayers;
    }

    @Nullable
    public String write() {
        try {
            JSONObject root = new JSONObject();
            root.put(KEY_BIOME_ID, biomeId);
            root.put(KEY_VERSION, encodingVersion);
            JSONArray jlayers = new JSONArray();
            for (Layer layer : mLayers) {
                JSONObject jlayer = new JSONObject();
                jlayer.put(KEY_BLOCK_NAME, "minecraft:" + layer.block.str);
                jlayer.put(KEY_BLOCK_DATA, layer.block.subId);
                jlayer.put(KEY_COUNT, layer.amount);
                jlayers.put(jlayer);
            }
            root.put(KEY_BLOCK_LAYERS, jlayers);
            if (hasStructureOps) root.put(KEY_STRUCTURE_OPS, null);
            return root.toString(4);
        } catch (JSONException e) {
            Log.d(FlatLayers.class, e);
        }
        return null;
    }

}
