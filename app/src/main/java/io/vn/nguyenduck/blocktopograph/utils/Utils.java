package io.vn.nguyenduck.blocktopograph.utils;

import android.os.Build;
import android.os.Environment;

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
        return Environment.getExternalStorageDirectory() + "/Android/data/" + appId + "/files";
    }

    public static String buildMinecraftDataDir(String parent, String folder) {
        return parent + "/games/com.mojang/" + folder;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}