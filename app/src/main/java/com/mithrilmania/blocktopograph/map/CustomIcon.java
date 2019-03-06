package com.mithrilmania.blocktopograph.map;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;

import com.mithrilmania.blocktopograph.util.NamedBitmapProvider;
import com.mithrilmania.blocktopograph.util.NamedBitmapProviderHandle;
import com.mithrilmania.blocktopograph.util.UV;

import java.io.IOException;
import java.util.HashMap;

/**
 * CustomIcon provides an easy collection of special icons to use for markers.
 */
public enum CustomIcon implements NamedBitmapProviderHandle, NamedBitmapProvider {

    DEFAULT_MARKER("default_marker", UV.ab(0, 0, 32, 32)),
    BLUE_MARKER("blue_marker", UV.ab(0, 32, 32, 64)),
    GREEN_MARKER("green_marker", UV.ab(32, 0, 64, 32)),
    RED_MARKER("red_marker", UV.ab(32, 32, 64, 64)),
    AQUA_MARKER("aqua_marker", UV.ab(64, 0, 96, 32)),
    ORANGE_MARKER("orange_marker", UV.ab(64, 32, 96, 64)),
    YELLOW_MARKER("yellow_marker", UV.ab(96, 0, 128, 32)),
    PURPLE_MARKER("purple_marker", UV.ab(96, 32, 128, 64)),
    SPAWN_MARKER("spawn_marker", UV.ab(64, 64, 128, 128));

    public final String iconName;
    public final UV uv;

    public Bitmap bitmap;

    CustomIcon(String iconName, UV uv){
        this.iconName = iconName;
        this.uv = uv;
    }

    @Override
    public Bitmap getBitmap(){
        return this.bitmap;
    }

    @NonNull
    @Override
    public NamedBitmapProvider getNamedBitmapProvider(){
        return this;
    }

    @NonNull
    @Override
    public String getBitmapDisplayName(){
        return this.iconName;
    }

    @NonNull
    @Override
    public String getBitmapDataName() {
        return this.iconName;
    }


    public static void loadCustomBitmaps(AssetManager assetManager) throws IOException {

        Bitmap sheet = BitmapFactory.decodeStream(assetManager.open("custom_icons.png"));
        for(CustomIcon icon : CustomIcon.values()){
            if(icon.bitmap == null && icon.uv != null){
                icon.bitmap = Bitmap.createBitmap(sheet,
                        icon.uv.uX, icon.uv.uY,
                        icon.uv.vX - icon.uv.uX, icon.uv.vY - icon.uv.uY,
                        null, false);
            }
        }
    }

    private static HashMap<String, CustomIcon> iconsByName;

    static {
        iconsByName = new HashMap<>();

        for(CustomIcon icon : CustomIcon.values()){
            iconsByName.put(icon.iconName, icon);
        }
    }

    public static CustomIcon getCustomIcon(String iconName){
        return iconsByName.get(iconName);
    }

}
