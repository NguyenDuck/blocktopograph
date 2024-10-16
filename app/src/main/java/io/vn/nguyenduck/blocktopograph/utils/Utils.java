package io.vn.nguyenduck.blocktopograph.utils;

import static io.vn.nguyenduck.blocktopograph.Constants.COM_MOJANG_FOLDER;

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
}