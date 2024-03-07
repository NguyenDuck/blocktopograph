package io.vn.nguyenduck.blocktopograph;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
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

//            dataIS.skip(4);
//            long length = Short.reverseBytes(dataIS.readShort());
//            LOGGER.info(String.valueOf(length));
//            dataIS.skip(2);

            Object[] data = readNBT(dataIS);
            dataBundle = NbtUtils.toBundle(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object[] readNBT(DataInputStream is) throws IOException {
        return readNBT(is, null, false);
    }

    private Object[] readNBT(DataInputStream is, Type tagType, boolean skipName) throws IOException {
        Type type = (tagType != null) ? tagType : Type.fromInt(is.read());
        String name = null;
        if (type != END && !skipName) {
            int length = Short.reverseBytes(is.readShort());
            byte[] dataReaded = new byte[length];
            is.readFully(dataReaded);
            name = new String(dataReaded);
        }
        Object value = null;
        switch (type) {
            case END:
                break;
            case BYTE:
                value = is.readByte();
                break;
            case SHORT:
                value = Short.reverseBytes(is.readShort());
                break;
            case INT:
                value = Integer.reverseBytes(is.readInt());
                break;
            case LONG:
                value = Long.reverseBytes(is.readLong());
                break;
            case FLOAT:
                value = Float.intBitsToFloat(Integer.reverseBytes(is.readInt()));
                break;
            case DOUBLE:
                value = Double.longBitsToDouble(Long.reverseBytes(is.readLong()));
                break;
            case BYTE_ARRAY: {
                int length = Integer.reverseBytes(is.readInt());
                byte[] data = new byte[length];
                is.readFully(data);
                value = data;
            }
            break;
            case STRING: {
                int length = Short.reverseBytes(is.readShort());
                byte[] data = new byte[length];
                is.readFully(data);
                value = new String(data);
            }
            break;
            case LIST: {
                Type childNbtType = Type.fromInt(is.readByte());
                int length = Integer.reverseBytes(is.readInt());
                value = new ArrayList<Object[]>();
                for (int i = 0; i < length; i++) {
                    ((ArrayList<Object[]>) value).add(readNBT(is, childNbtType, true));
                }
                return new Object[]{type, name, value, childNbtType};
            }
            case COMPOUND: {
                value = new ArrayList<Object[]>();
                while (true) {
                    Object[] data = readNBT(is);
                    if (data[0] == END) break;
                    else ((ArrayList<Object[]>) value).add(data);
                }
            }
            break;
            case INT_ARRAY: {
                int length = Integer.reverseBytes(is.readInt());
                int[] data = new int[length];
                for (int i = 0; i < length; i++) {
                    data[i] = Integer.reverseBytes(is.readInt());
                }
                value = data;
            }
            break;
            case SHORT_ARRAY: {
                int length = Integer.reverseBytes(is.readInt());
                short[] data = new short[length];
                for (int i = 0; i < length; i++) {
                    data[i] = Short.reverseBytes(is.readShort());
                }
                value = data;
            }
            break;
        }
        return new Object[]{type, name, value};
    }
}
