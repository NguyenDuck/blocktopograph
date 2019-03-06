package com.mithrilmania.blocktopograph.util;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;

import com.mithrilmania.blocktopograph.Log;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class IoUtil {

    /**
     * Returns the size of a folder (total of contents) in number of bytes.
     * Returns 0 if it doesn't exist.
     *
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
     *
     * @param f file to return size of
     * @return size, formatted with a "B", "MB, "GB" or "TB", precise to 2 decimals.
     */
    public static String getFileSizeInText(File f) {
        long size = getFolderSize(f);
        if (size < 1024) return size + " B";
        double v = size / 1024.0;
        String suffix = "KB";
        if (v > 1024.0) {
            v /= 1024.0;
            if (v > 1024.0) {
                v /= 1024.0;
                if (v > 1024.0) {
                    v /= 1024.0;
                    suffix = "TB";//very high end android device here
                } else suffix = "GB";
            } else suffix = "MB";
        }

        return String.format(Locale.ENGLISH, "%.2f %s", v, suffix);

    }

    /**
     * Extract file from app asset to file system.
     *
     * @param mgr  Asset manager instance.
     * @param name Asset path. No slash at beginning or end.
     * @param mode Asset access mode used in AssetManager.open().
     * @param out  Output file path. Shall be accessible.
     * @return Success or not.
     */
    public static boolean extractAsset(AssetManager mgr, String name, int mode, File out) {
        InputStream is = null;
        FileOutputStream fos = null;
        boolean ret = false;
        try {
            is = mgr.open(name, AssetManager.ACCESS_BUFFER);
            fos = new FileOutputStream(out);
            //FileUtil.copyStream(is, fos);
            ret = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (is != null) is.close();
        } catch (Exception ignored) {
        }
        try {
            if (fos != null) fos.close();
        } catch (Exception ignored) {
        }
        return ret;
    }

    /**
     * Write string to a text file.
     *
     * @param file    Destination file.
     * @param content String to be written.
     * @return Success or not.
     */
    static public boolean writeTextFile(File file, String content) {
        FileOutputStream fos = null;
        boolean res = true;
        try {
            fos = new FileOutputStream(file);
        } catch (Exception e) {
            Log.d(McUtil.class, e);
            return false;
        }
        try {
            byte[] bytes = content.getBytes(Charset.defaultCharset());
            fos.write(bytes);
        } catch (Exception e) {
            Log.d(McUtil.class, e);
            res = false;
        } finally {
            try {
                fos.close();
            } catch (Exception ignored) {
            }
        }
        return res;
    }

    static public boolean writeBinaryFile(File file, byte[] content) {
        FileOutputStream fos = null;
        boolean res = true;
        try {
            fos = new FileOutputStream(file);
        } catch (Exception e) {
            Log.d(McUtil.class, e);
            return false;
        }
        try {
            fos.write(content);
        } catch (Exception e) {
            Log.d(McUtil.class, e);
            res = false;
        } finally {
            try {
                fos.close();
            } catch (Exception ignored) {
            }
        }
        return res;
    }

    /**
     * Read text file into String.
     *
     * @param txtFile File to be read.
     * @return Read content. Null if failed.
     */
    @Nullable
    public static String readTextFile(@NonNull File txtFile) {
        StringBuilder text = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(txtFile));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line).append('\n');
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return text.toString();
    }

    /**
     * Read first line of a text file. Designed for levelname.txt.
     *
     * @param txtFile File to be read.
     * @return Read content. Null if failed.
     */
    public static String readTextFileFirstLine(File txtFile) {
        String text = null;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(txtFile));
            text = br.readLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    /**
     * Make sure a certain directory exists and is a directory instead of other types of file.
     * <p>
     * Will create the directory if not exist.
     * </p>
     *
     * @param dir Directory to be verified or created.
     * @return Whether it's verified or failed for a reason.
     */
    public static Errno makeSureDirIsDir(@NonNull File dir) {
        if (dir.exists()) {
            if (!dir.isDirectory()) return Errno.FILE_WITH_SAME_NAME_EXISTS;
        } else {
            if (!dir.mkdirs()) return Errno.CANNOT_MKDIRS;
        }
        return Errno.OK;
    }

    /**
     * Given a target file, add some variation to avoid name conflicts with existing file.
     * <p>
     * For instance "/sdcard/xxx/meow.txt" may be a wanted name,
     * but there exist "meow.txt", "meow (1).txt" and then
     * "meow (2).txt have to be used.
     * </p>
     *
     * @param parent Directory the target file would reside.
     * @param lhalf  Left half of file name. E.g. "meow" for "meow ($count).txt".
     * @param rhalf  Right half of file name. E.g. ".txt" for "meow ($count).txt".
     * @param prefix Prefix for the dynamic part. E.g. "(" for "meow ($count).txt".
     * @param suffix Suffix for the dynamic part. E.g. ")" for "meow ($count).txt".
     * @return The available name, or null if failed too many times.
     */
    @Nullable
    public static File getFileWithFirstAvailableName(@NonNull File parent, @NonNull String lhalf,
                                                     @NonNull String rhalf, @NonNull String prefix,
                                                     @NonNull String suffix) {
        StringBuilder sb = new StringBuilder();
        sb.append(lhalf).append(rhalf);
        File target = new File(parent, sb.toString());
        if (!target.exists()) return target;
        int count = 0;
        do {
            count++;
            if (count > 999) return null;
            sb = new StringBuilder(256).append(lhalf).append(prefix)
                    .append(count).append(suffix).append(rhalf);
            target = new File(parent, sb.toString());
        } while (target.exists());
        return target;
    }

    public enum Errno {
        OK, FILE_WITH_SAME_NAME_EXISTS, CANNOT_MKDIRS,
        PERMISSION_DENIED, UNKNOWN
    }

    /**
     * Check if app has storage write permission. Use after requested permission in a previous activity.
     *
     * @param context Context.
     * @return True if has permission, otherwise false.
     */
    public static boolean hasWritePermission(@NonNull Context context) {
        int permission = ActivityCompat.checkSelfPermission(
                context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Save a bitmap to file using Android's compress methods.
     *
     * @param bmp        bitmap to save.
     * @param format     format required by Android. Png, Jpeg and Webp supported.
     * @param quality    Quality if using Jpeg.
     * @param dir        Directory to save to, should be folder not ordinary file.
     * @param baseName   Name of the file, without extension.
     * @param autoRename Should file be automatically renamed to "xxx (1).jpg" things
     *                   if a conflict happened.
     * @return The file the bitmap was saved to, null if failed.
     */
    @Nullable
    public static File saveBitmap(@NotNull Bitmap bmp, @NotNull Bitmap.CompressFormat format,
                                  int quality, @NotNull File dir, @NotNull String baseName,
                                  boolean autoRename) {
        String extension;
        if (!dir.exists()) {
            if (autoRename) {
                // If allowed to auto rename lets assume mkdir's also reasonable.
                if (makeSureDirIsDir(dir) != Errno.OK) return null;
            } else return null;
        }
        switch (format) {
            case JPEG:
                extension = ".jpg";
                break;
            case PNG:
                extension = ".png";
                break;
            case WEBP:
                extension = ".webp";
                break;
            default:
                return null;
        }
        File saveTo = autoRename ?
                getFileWithFirstAvailableName(dir, baseName, extension, "(", ")")
                : new File(dir, baseName + extension);
        if (saveTo == null) return null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(saveTo);
            bmp.compress(format, quality, fos);
            fos.close();
            return saveTo;
        } catch (Exception e) {
            Log.d(IoUtil.class, e);
            if (fos != null) try {
                fos.close();
            } catch (Exception ignore) {
            }
            return null;
        }
    }
}
