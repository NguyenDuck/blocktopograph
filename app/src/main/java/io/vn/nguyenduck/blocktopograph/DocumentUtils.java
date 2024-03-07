package io.vn.nguyenduck.blocktopograph;

import android.content.ContentResolver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.documentfile.provider.DocumentFile;

import java.util.Objects;
import java.util.logging.Logger;

import static io.vn.nguyenduck.blocktopograph.Constants.SELF_APP_NAME;

public class DocumentUtils {
    public static ContentResolver contentResolver;

    @Nullable
    public static DocumentFile getFileFromPath(@NonNull DocumentFile root, @NonNull String[] path) {
        DocumentFile f = root;
        for (String p : path) {
            if (f == null) break;
            f = f.findFile(p);
        }
        return f;
    }

    public static DocumentFile findFiles(@NonNull DocumentFile root, @NonNull String startWiths) {
        for (DocumentFile f : root.listFiles()) {
            String name = f.getName();
            if (name != null && name.startsWith(startWiths)) return f;
        }
        return null;
    }
}
