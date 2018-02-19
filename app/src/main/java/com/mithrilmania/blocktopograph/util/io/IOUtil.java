package com.mithrilmania.blocktopograph.util.io;


import java.io.File;
import java.util.Locale;

public class IOUtil {

    /**
     * Returns the size of a folder (total of contents) in number of bytes.
     * Returns 0 if it doesn't exist.
     * @param f folder to get size of.
     * @return size of folder f in bytes.
     */
    public static long getFolderSize(File f) {
        long size = 0;
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                size += getFolderSize(file);
            }
        } else {
            size = f.length();
        }
        return size;
    }

    /**
     * Simple helper function to get the size of a folder or file in text format,
     * @param f file to return size of
     * @return size, formatted with a "B", "MB, "GB" or "TB", precise to 2 decimals.
     */
    public static String getFileSizeInText(File f){
        long size = getFolderSize(f);
        if(size < 1024) return size + " B";
        double v = size / 1024.0;
        String suffix = "KB";
        if(v > 1024.0){
            v /= 1024.0;
            if(v > 1024.0){
                v /= 1024.0;
                if(v > 1024.0){
                    v /= 1024.0;
                    suffix = "TB";//very high end android device here
                }
                else suffix = "GB";
            }
            else suffix = "MB";
        }

        return String.format(Locale.ENGLISH, "%.2f %s", v, suffix);

    }
}
