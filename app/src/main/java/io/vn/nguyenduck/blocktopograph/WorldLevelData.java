package io.vn.nguyenduck.blocktopograph;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import io.vn.nguyenduck.blocktopograph.nbt.NbtInputStream;
import io.vn.nguyenduck.blocktopograph.nbt.Type;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static io.vn.nguyenduck.blocktopograph.Constants.*;
import static io.vn.nguyenduck.blocktopograph.DocumentUtils.*;
import static io.vn.nguyenduck.blocktopograph.Logger.LOGGER;
import static io.vn.nguyenduck.blocktopograph.nbt.Type.*;

public class WorldLevelData {

    private final DocumentFile root;
    public String rawWorldName;
    public Bundle dataBundle = new Bundle();
    public Uri worldIconUri;
    public long worldSizeRaw;
    public String worldSizeFormated;

    public WorldLevelData(@NonNull DocumentFile root) {
        this.root = root;
        readRawWorldName();
        readAllTags();
        setupWorldIconUri();
        setupWorldSize();
    }

    private void setupWorldIconUri() {
        DocumentFile doc = findFiles(root, WORLD_ICON_PREFIX);
        if (doc != null) worldIconUri = doc.getUri();
    }

    private void setupWorldSize() {
        worldSizeRaw = calculateFolderSize(root);
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(worldSizeRaw) / Math.log10(1024));
        worldSizeFormated = new DecimalFormat("#,##0.#")
                .format(worldSizeRaw / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public long calculateFolderSize(DocumentFile folder) {
        long totalSize = 0;
        if (folder.isDirectory()) {
            for (DocumentFile file : folder.listFiles()) {
                if (file.isFile()) {
                    totalSize += file.length();
                } else if (file.isDirectory()) {
                    totalSize += calculateFolderSize(file);
                }
            }
        }
        return totalSize;
    }

    private void readRawWorldName() {
        try {
            DocumentFile doc = DocumentUtils.getFileFromPath(root, new String[]{WORLD_LEVELNAME_FILE});
            if (doc == null) return;
            InputStream is = contentResolver.openInputStream(doc.getUri());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String name = reader.readLine();
            if (name != null) rawWorldName = name;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void readAllTags() {
        try {
            DocumentFile doc = DocumentUtils.getFileFromPath(root, new String[]{WORLD_LEVEL_DATA_FILE});
            if (doc == null) return;
            InputStream is = contentResolver.openInputStream(doc.getUri());
            DataInputStream dataIS = new DataInputStream(is);
            dataIS.skip(8);

            NbtInputStream data = new NbtInputStream(dataIS);
            dataBundle = NbtUtils.toBundle(data.readTag());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
