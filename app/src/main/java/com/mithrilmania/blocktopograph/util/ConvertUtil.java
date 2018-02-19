package com.mithrilmania.blocktopograph.util;

/**
 * Convert utils
 */
public class ConvertUtil {

    public static String bytesToHexStr(byte[] in) {
        if(in == null) return "null";
        final StringBuilder builder = new StringBuilder();
        for(byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }


}
