package com.mithrilmania.blocktopograph.util;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import java.io.File;

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
