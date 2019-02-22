package com.mithrilmania.blocktopograph.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Convert utils
 */
public class ConvertUtil {

    public static String bytesToHexStr(byte[] in) {
        if (in == null) return "null";
        final StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    private static int hexCharToByte(char ch) {
        switch (ch) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'a':
                return 10;
            case 'b':
                return 11;
            case 'c':
                return 12;
            case 'd':
                return 13;
            case 'e':
                return 14;
            case 'f':
                return 15;
            case 'A':
                return 10;
            case 'B':
                return 11;
            case 'C':
                return 12;
            case 'D':
                return 13;
            case 'E':
                return 14;
            case 'F':
                return 15;
            default:
                return -1;
        }
    }

    @Nullable
    public static byte[] hexStringToBytes(@NonNull String text) {
        byte[] ret;
        if (text.charAt(0) == '0' && (text.charAt(1) == 'x' || text.charAt(1) == 'X'))
            text = text.substring(2);
        flow:
        {
            int len = text.length();
            if ((len & 1) != 0) {
                ret = null;
                break flow;
            }
            len = len >> 1;
            ret = new byte[len];
            for (int i = 0; i < len; i++) {
                int h = hexCharToByte(text.charAt(i << 1)) << 4;
                if (h < 0) break flow;
                int l = hexCharToByte(text.charAt(i << 1 | 1));
                if (l < 0) break flow;
                ret[i] = (byte) (h | l);
            }
        }
        return ret;
    }

    public static String getLegalFileName(String text) {
        return text.replaceAll("[\\\\/:*?\"<>|.]", "_");
    }

}
