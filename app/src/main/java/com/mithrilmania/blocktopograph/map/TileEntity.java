package com.mithrilmania.blocktopograph.map;

import android.content.res.AssetManager;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.mithrilmania.blocktopograph.block.ListingBlock;
import com.mithrilmania.blocktopograph.util.NamedBitmapProvider;
import com.mithrilmania.blocktopograph.util.NamedBitmapProviderHandle;

import java.util.HashMap;
import java.util.Map;


public enum TileEntity implements NamedBitmapProviderHandle, NamedBitmapProvider {

    CHEST(0, "Chest", "Chest", ListingBlock.B_54_CHEST),
    TRAPPED_CHEST(1, "Trapped Chest", "TrappedChest", ListingBlock.B_146_TRAPPED_CHEST),
    ENDER_CHEST(2, "Ender Chest", "EnderChest", ListingBlock.B_130_ENDER_CHEST),
    MOB_SPAWNER(3, "Mob Spawner", "MobSpawner", ListingBlock.B_52_MOB_SPAWNER),
    END_PORTAL(4, "End Portal", "EndPortal", ListingBlock.B_119_END_PORTAL),
    BEACON(5, "Beacon", "Beacon", ListingBlock.B_138_BEACON);


    private static final Map<String, TileEntity> tileEntityMap;
    private static final Map<Integer, TileEntity> tileEntityByID;

    public final int id;
    public final String displayName, dataName;

    static {
        tileEntityMap = new HashMap<>();
        tileEntityByID = new HashMap<>();
        for (TileEntity e : TileEntity.values()) {
            tileEntityMap.put(e.dataName, e);
            tileEntityByID.put(e.id, e);
        }
    }

    public final ListingBlock block;
    public Bitmap icon;

    TileEntity(int id, String displayName, String dataName, ListingBlock block) {
        this.id = id;
        this.displayName = displayName;
        this.dataName = dataName;
        this.block = block;
    }

    static public synchronized void loadIcons(AssetManager assMan) {
        for (TileEntity entity : values())
            if (entity.icon == null)
                entity.icon = Bitmap.createScaledBitmap(entity.block.getIcon(assMan),
                        36, 36, false);
    }

    public static TileEntity getTileEntity(int id) {
        return tileEntityByID.get(id);
    }

    public static TileEntity getTileEntity(String dataName) {
        return tileEntityMap.get(dataName);
    }

    @NonNull
    @Override
    public String getBitmapDataName() {
        return this.dataName;
    }

    @Override
    public Bitmap getBitmap() {
        return icon;
    }

    @NonNull
    @Override
    public NamedBitmapProvider getNamedBitmapProvider() {
        return this;
    }

    @NonNull
    @Override
    public String getBitmapDisplayName() {
        return this.displayName;
    }


}
