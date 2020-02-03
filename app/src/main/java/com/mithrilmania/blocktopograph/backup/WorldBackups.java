package com.mithrilmania.blocktopograph.backup;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.World;
import com.mithrilmania.blocktopograph.util.IoUtil;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class WorldBackups {

    public static final String BTG_BACKUPS = "btgBackups";
    private static final String AUTO_BACKUP = "auto_backup";
    private static final String AUTO_DELETE = "auto_delete";
    // We're using this in file name so it should not be location-specific.
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd-HHmm-SSss'-'");
    public boolean autoBackup;
    public boolean autoDelete = true;
    @NonNull
    private World mWorld;

    public WorldBackups(@NonNull World world) {
        mWorld = world;
    }


    @NonNull
    private File getBackupDir() {
        return new File(mWorld.worldFolder, BTG_BACKUPS);
    }


    @NonNull
    private File getConfigFile() {
        return new File(getBackupDir(), "config.json");
    }

    public void loadConfig() {
        File cfg = getConfigFile();
        if (!cfg.isFile()) return;
        try {
            JSONObject obj = new JSONObject(FileUtils.readFileToString(cfg));
            autoBackup = obj.getBoolean(AUTO_BACKUP);
            autoDelete = obj.getBoolean(AUTO_DELETE);
        } catch (Exception e) {
            Log.d(this, e);
        }
    }

    public void setAutoBackup(boolean value, @Nullable String readMe) {
        if (autoBackup == value) return;
        autoBackup = value;
        saveConfig();
        if (readMe != null) makeSureReadMeExists(readMe);
    }

    public void setAutoDelete(boolean value, @Nullable String readMe) {
        if (autoDelete == value) return;
        autoDelete = value;
        saveConfig();
        if (readMe != null) makeSureReadMeExists(readMe);
    }

    public void saveConfig() {
        File dir = getBackupDir();
        if (IoUtil.makeSureDirIsDir(dir) != IoUtil.Errno.OK) return;
        try {
            JSONObject obj = new JSONObject();
            obj.put(AUTO_BACKUP, autoBackup);
            obj.put(AUTO_DELETE, autoDelete);
            FileUtils.writeStringToFile(getConfigFile(), obj.toString(4));
        } catch (Exception e) {
            Log.d(this, e);
        }
    }

    private void makeSureReadMeExists(String readMe) {
        File dir = getBackupDir();
        if (IoUtil.makeSureDirIsDir(dir) != IoUtil.Errno.OK) return;
        File file = new File(dir, "readme.txt");
        if (file.exists()) return;
        try {
            FileUtils.writeStringToFile(file, readMe);
        } catch (IOException e) {
            Log.d(this, e);
        }
    }

    public boolean createNewBackup(String name, Date time) {
        try {
            File bakDir = getBackupDir();
            if (IoUtil.makeSureDirIsDir(bakDir) != IoUtil.Errno.OK) return false;
            File zfile = IoUtil.getFileWithFirstAvailableName(bakDir, DATE_FORMAT.format(time) + name, ".zip", "(", ")");
            ZipFile zip = new ZipFile(zfile);
            for (File file : Objects.requireNonNull(getFilesInWorldDirExceptBackupDir())) {
                if (file.isDirectory()) zip.addFolder(file);
                else zip.addFile(file);
            }
        } catch (Exception e) {
            Log.e(this, e);
            return false;
        }
        cleanOldBackups(time);
        return true;
    }

    public boolean restoreBackup(@NonNull Backup backup) {
        ZipFile zip = new ZipFile(backup.file);
        if (!zip.isValidZipFile()) return false;
        File[] files = getFilesInWorldDirExceptBackupDir();
        if (files == null) return false;
        for (File file : files)
            if (!FileUtils.deleteQuietly(file)) return false;
        try {
            zip.extractAll(mWorld.worldFolder.getAbsolutePath());
        } catch (ZipException e) {
            Log.d(this, e);
            return false;
        }
        return true;
    }

    public boolean deleteBackup(@NonNull Backup backup) {
        return backup.file.delete();
    }

    public void cleanOldBackups(@NonNull Date now) {
        if (!autoDelete) return;
        Backup[] baks = getBackups();
        if (baks == null) return;
        for (int count = baks.length - 1; count > 2; count--) {
            long diff = (now.getTime() - baks[count].time.getTime()) / 1000L;
            long THREE_DAYS = 259200L;
            long ONE_YEAR = 31536000L;
            if (diff < THREE_DAYS) break;
            // Keep old backups.
            if (diff < ONE_YEAR) deleteBackup(baks[count]);
        }
    }


    @Nullable
    private File[] getFilesInWorldDirExceptBackupDir() {
        return mWorld.worldFolder.listFiles((file, s) -> !s.equals(BTG_BACKUPS));
    }


    @Nullable
    public Backup[] getBackups() {
        File bakDir = getBackupDir();
        if (!bakDir.isDirectory()) return null;
        File[] files = bakDir.listFiles((file, s) -> s.endsWith(".zip"));
        if (files == null) return null;
        Arrays.sort(files);
        ArrayUtils.reverse(files);
        Backup[] baks = new Backup[files.length];
        for (int i = 0; i < files.length; i++) {
            String name = FilenameUtils.getBaseName(files[i].getName());
            ParsePosition pos = new ParsePosition(0);
            Date time = DATE_FORMAT.parse(name, pos);
            if (time == null) {
                time = new Date(0);
            } else {
                name = name.substring(pos.getIndex());
            }
            baks[i] = new Backup(name, time, files[i], FileUtils.byteCountToDisplaySize(FileUtils.sizeOf(files[i])));
        }
        return baks;
    }
}
