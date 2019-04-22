package com.mithrilmania.blocktopograph.map;

import android.graphics.Bitmap;

import com.mithrilmania.blocktopograph.util.NamedBitmapProvider;
import com.mithrilmania.blocktopograph.util.NamedBitmapProviderHandle;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;


public enum TileEntity implements NamedBitmapProviderHandle, NamedBitmapProvider {

    CHEST(0, "Chest", "Chest", KnownBlock.B_54_0_CHEST),
    TRAPPED_CHEST(1, "Trapped Chest", "TrappedChest", KnownBlock.B_146_0_TRAPPED_CHEST),
    ENDER_CHEST(2, "Ender Chest", "EnderChest", KnownBlock.B_130_0_ENDER_CHEST),
    MOB_SPAWNER(3, "Mob Spawner", "MobSpawner", KnownBlock.B_52_0_MOB_SPAWNER),
    END_PORTAL(4, "End Portal", "EndPortal", KnownBlock.B_119_0_END_PORTAL),
    BEACON(5, "Beacon", "Beacon", KnownBlock.B_138_0_BEACON);


    public final int id;
    public final String displayName, dataName;

    public final KnownBlock block;

    TileEntity(int id, String displayName, String dataName, KnownBlock block) {
        this.id = id;
        this.displayName = displayName;
        this.dataName = dataName;
        this.block = block;
    }

    @Override
    public Bitmap getBitmap(){
        return block.bitmap;
    }

    @NonNull
    @Override
    public NamedBitmapProvider getNamedBitmapProvider(){
        return this;
    }

    @NonNull
    @Override
    public String getBitmapDisplayName(){
        return this.displayName;
    }

    @NonNull
    @Override
    public String getBitmapDataName() {
        return this.dataName;
    }

    private static final Map<String, TileEntity> tileEntityMap;
    private static final Map<Integer, TileEntity> tileEntityByID;

    static {
        tileEntityMap = new HashMap<>();
        tileEntityByID = new HashMap<>();
        for(TileEntity e : TileEntity.values()){
            tileEntityMap.put(e.dataName, e);
            tileEntityByID.put(e.id, e);
        }
    }

    public static TileEntity getTileEntity(int id){
        return tileEntityByID.get(id);
    }

    public static TileEntity getTileEntity(String dataName){
        return tileEntityMap.get(dataName);
    }

    
}
