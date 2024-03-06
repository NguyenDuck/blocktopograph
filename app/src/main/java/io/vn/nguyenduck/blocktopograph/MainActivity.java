package io.vn.nguyenduck.blocktopograph;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.logging.Logger;

import static io.vn.nguyenduck.blocktopograph.Constants.*;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_FOLDER_PERMISSION = 0x00a;
    private static final String ACCESS_URI = "access_uri";
    private static Logger logger;
    private static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger = Logger.getLogger(this.getString(R.string.app_name));
        preferences = getPreferences(MODE_PRIVATE);
    }

    private boolean hasPermission() {
        boolean isGrantedPermission = false;

        if (preferences.contains(ACCESS_URI)) {
            Uri oldUri = Uri.parse(preferences.getString(ACCESS_URI, ""));
            DocumentFile f = DocumentFile.fromTreeUri(this, oldUri);
            if (f != null && f.canWrite()) {
                isGrantedPermission = true;
                logger.info(String.valueOf(f.canWrite()));
            }
        }
        return isGrantedPermission;
    }

    private void displayStoragePermissionAlert() {
        new AlertDialog.Builder(this, R.style.Dialog_StoragePermission)
                .setTitle(R.string.storage_permission_title)
                .setMessage(R.string.storage_permission_description)
                .setPositiveButton(
                        android.R.string.ok,
                        (DialogInterface dialog, int which) -> {
                            requestPermission();
                        })
                .setCancelable(false)
                .create()
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!hasPermission()) displayStoragePermissionAlert();
    }

    private void requestPermission() {
        try {
            Uri uri = DocumentsContract.buildDocumentUri(
                    DOC_AUTHORITY,
                    new StringJoiner("/")
                            .add(DOC_DATA_PATH)
                            .add(MINECRAFT_APP_ID)
                            .toString()
            );

            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
                    .putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri)
                    .addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    .addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(intent, REQ_FOLDER_PERMISSION);

        } catch (RuntimeException e) {
            logger.throwing(this.getClass().toString(), "onCreate", e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        try {
            if (resultCode == Activity.RESULT_OK && requestCode == REQ_FOLDER_PERMISSION) {
                Uri uri = null;
                if (resultData != null) uri = resultData.getData();

                if (uri != null) {
                    if (!Objects.equals(uri.getPath(), "/tree/primary:Android/data/com.mojang.minecraftpe")) return;

                    getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    preferences.edit().putString(ACCESS_URI, uri.toString()).apply();
                    DocumentFile f = DocumentFile.fromTreeUri(this, uri);
                    if (f != null) logger.info(String.valueOf(f.canWrite()));
                }
            }
        } catch (Exception e) {
            logger.throwing(this.getClass().toString(), "onActivityResult", e);
        }
        super.onActivityResult(requestCode, resultCode, resultData);
    }
}