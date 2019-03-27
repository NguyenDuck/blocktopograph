package com.mithrilmania.blocktopograph;

import android.view.ViewGroup;

import com.mithrilmania.blocktopograph.chunk.NBTChunkData;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.map.marker.AbstractMarker;
import com.mithrilmania.blocktopograph.map.renderer.MapType;
import com.mithrilmania.blocktopograph.nbt.EditableNBT;

//TODO the structure of MapFragment <-> WorldActivity communication should be improved,
// this spec consists of methods that were added on-the-fly when adding features; no coherence...
public interface WorldActivityInterface {


    World getWorld();

    Dimension getDimension();

    MapType getMapType();

    boolean getShowGrid();

    boolean getShowMarkers();

    void onFatalDBError(WorldData.WorldDBException worldDB);

    void addMarker(AbstractMarker marker);

    void showActionBar();

    void hideActionBar();

    void openDrawer();

    EditableNBT getEditablePlayer() throws Exception;

    void changeMapType(MapType mapType, Dimension dimension);

    void openChunkNBTEditor(final int chunkX, final int chunkZ, final NBTChunkData nbtChunkData, final ViewGroup viewGroup);
}
