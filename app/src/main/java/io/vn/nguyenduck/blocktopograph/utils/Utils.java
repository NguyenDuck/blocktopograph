package io.vn.nguyenduck.blocktopograph.utils;

import static io.vn.nguyenduck.blocktopograph.Constants.COM_MOJANG_FOLDER;

import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Utils {
    public static boolean includes(byte[] sub, byte[] main) {
        for (int i = 0; i <= main.length - sub.length; i++) {
            int j;
            for (j = 0; j < sub.length; j++) {
                if (main[i + j] != sub[j]) break;
                if (j == sub.length - 1) return true;
            }
        }
        return false;
    }

    public static boolean isAndroid11Up() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    public static String buildAndroidDataDir(String appId) {
        return String.format("%s/Android/data/%s/files", Environment.getExternalStorageDirectory(), appId);
    }

    public static String buildMinecraftDataDir(String parent, String folder) {
        return String.format("%s/games/%s/%s", parent, COM_MOJANG_FOLDER, folder);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public static long getSizeOf(File f) {
        long size = 0;
        for (File file : Objects.requireNonNull(f.listFiles())) {
            if (file.isDirectory()) {
                size += getSizeOf(file);
            } else size += file.length();
        }
        return size;
    }

    public static String translateSizeToString(long size) {
        if (size <= 0) return "0 B";
        var units = List.of("B", "KB", "MB", "GB", "TB");
        var digitGroups = (int) (Math.log10(size) / Math.log10(1024.0));
        return String.format(Locale.getDefault(), "%.2f %s", size / Math.pow(1024.0, digitGroups), units.get(digitGroups));
    }
}