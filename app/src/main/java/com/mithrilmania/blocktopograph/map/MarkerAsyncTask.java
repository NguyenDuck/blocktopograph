package com.mithrilmania.blocktopograph.map;

import android.os.AsyncTask;
import com.mithrilmania.blocktopograph.Log;

import com.mithrilmania.blocktopograph.WorldActivityInterface;
import com.mithrilmania.blocktopograph.chunk.*;
import com.mithrilmania.blocktopograph.map.marker.AbstractMarker;
import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.FloatTag;
import com.mithrilmania.blocktopograph.nbt.tags.IntTag;
import com.mithrilmania.blocktopograph.nbt.tags.ListTag;
import com.mithrilmania.blocktopograph.nbt.tags.StringTag;
import com.mithrilmania.blocktopograph.nbt.tags.Tag;

import java.util.Collection;
import java.util.List;

/**
 * Load the NBT of the chunks and output the markers, async with both map-rendering and UI
 */
public class MarkerAsyncTask extends AsyncTask<Void, AbstractMarker, Void> {

    private final WorldActivityInterface worldProvider;
    private final ChunkManager chunkManager;

    private final int minChunkX, minChunkZ, maxChunkX, maxChunkZ;


    public MarkerAsyncTask(WorldActivityInterface worldProvider, ChunkManager chunkManager, int minChunkX, int minChunkZ, int maxChunkX, int maxChunkZ){
        this.minChunkX = minChunkX;
        this.minChunkZ = minChunkZ;
        this.maxChunkX = maxChunkX;
        this.maxChunkZ = maxChunkZ;

        this.worldProvider = worldProvider;
        this.chunkManager = chunkManager;
    }

    @Override
    protected Void doInBackground(Void... v) {

        int cX, cZ;
        for(cZ = minChunkZ; cZ < maxChunkZ; cZ++){
            for(cX = minChunkX; cX < maxChunkX; cX++){
                loadEntityMarkers(cX, cZ);
                loadTileEntityMarkers(cX, cZ);
                loadCustomMarkers(cX, cZ);
            }
        }

        return null;
    }

    private void loadEntityMarkers(int chunkX, int chunkZ){
        try {
            Chunk chunk = chunkManager.getChunk(chunkX, chunkZ);

            NBTChunkData entityData = chunk.getEntity();

            if (entityData == null) return;

            entityData.load();

            if(entityData.tags == null) return;

            for (Tag tag : entityData.tags) {
                if (tag instanceof CompoundTag) {
                    CompoundTag compoundTag = (CompoundTag) tag;
                    //int id = ((IntTag) compoundTag.getChildTagByKey("id")).getValue();
                    String tempName = compoundTag.getChildTagByKey("identifier").getValue().toString();
                    tempName = tempName.replace("minecraft:","");
                    tempName =  tempName.substring(0, 1).toUpperCase() + tempName.substring(1);
                    int id = Entity.getEntity(tempName).id;
                    Entity e = Entity.getEntity(id & 0xff);
                    if (e != null && e.bitmap != null) {
                        List<Tag> pos = ((ListTag) compoundTag.getChildTagByKey("Pos")).getValue();
                        float xf = ((FloatTag) pos.get(0)).getValue();
                        float yf = ((FloatTag) pos.get(1)).getValue();
                        float zf = ((FloatTag) pos.get(2)).getValue();

                        this.publishProgress(new AbstractMarker(Math.round(xf), Math.round(yf), Math.round(zf), chunkManager.dimension, e, false));
                    }
                }
            }

        } catch (Exception e){
            Log.w(e.getMessage());
        }
    }

    private void loadTileEntityMarkers(int chunkX, int chunkZ){
        try {
            Chunk chunk = chunkManager.getChunk(chunkX, chunkZ);

            NBTChunkData tileEntityData = chunk.getBlockEntity();

            if (tileEntityData == null) return;

            tileEntityData.load();

            if(tileEntityData.tags == null) return;

            for (Tag tag : tileEntityData.tags) {
                if (tag instanceof CompoundTag) {
                    CompoundTag compoundTag = (CompoundTag) tag;
                    String name = ((StringTag) compoundTag.getChildTagByKey("id")).getValue();
                    TileEntity te = TileEntity.getTileEntity(name);
                    if (te != null && te.getBitmap() != null) {
                        int eX = ((IntTag) compoundTag.getChildTagByKey("x")).getValue();
                        int eY = ((IntTag) compoundTag.getChildTagByKey("y")).getValue();
                        int eZ = ((IntTag) compoundTag.getChildTagByKey("z")).getValue();

                        this.publishProgress(new AbstractMarker(Math.round(eX), Math.round(eY), Math.round(eZ), chunkManager.dimension, te, false));
                    }
                }
            }

        } catch (Exception e){
            Log.w(e.getMessage());
        }
    }

    private void loadCustomMarkers(int chunkX, int chunkZ){
        Collection<AbstractMarker> chunk = worldProvider.getWorld().getMarkerManager()
                .getMarkersOfChunk(chunkX, chunkZ);
        AbstractMarker[] markers = new AbstractMarker[chunk.size()];
        this.publishProgress(chunk.toArray(markers));
    }

    @Override
    protected void onProgressUpdate(AbstractMarker... values) {
        for(AbstractMarker marker : values){
            worldProvider.addMarker(marker);
        }
    }


}
