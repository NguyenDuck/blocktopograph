package com.mithrilmania.blocktopograph.util;

import androidx.annotation.NonNull;

import java.io.File;

public final class McUtil {

    @NonNull

    public static File getMinecraftWorldsDir(File sdcard) {
        return new File(sdcard, "games/com.mojang/minecraftWorlds");
    }

    @NonNull

    public static File getBtgTestDir(File sdcard) {
        return new File(sdcard, "games/com.mojang/btgTest");
    }

    @NonNull

    public static File getLevelDatFile(File world) {
        return new File(world, "level.dat");
    }

    @NonNull

    public static File getLevelNameFile(File world) {
        return new File(world, "levelname.txt");
    }

}
