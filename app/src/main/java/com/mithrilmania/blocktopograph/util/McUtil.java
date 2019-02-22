package com.mithrilmania.blocktopograph.util;

import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import com.mithrilmania.blocktopograph.Log;

import org.jetbrains.annotations.Contract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public final class McUtil {

    @NonNull
    @Contract("_ -> new")
    public static File getMinecraftWorldsDir(File sdcard) {
        return new File(sdcard, "games/com.mojang/minecraftWorlds");
    }

    @NonNull
    @Contract("_ -> new")
    public static File getBtgTestDir(File sdcard) {
        return new File(sdcard, "games/com.mojang/btgTest");
    }

    @NonNull
    @Contract("_ -> new")
    public static File getLevelDatFile(File world) {
        return new File(world, "level.dat");
    }

    @NonNull
    @Contract("_ -> new")
    public static File getLevelNameFile(File world) {
        return new File(world, "levelname.txt");
    }

}
