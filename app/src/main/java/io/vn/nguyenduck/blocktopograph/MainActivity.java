package io.vn.nguyenduck.blocktopograph;

import android.annotation.SuppressLint;
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
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;
import java.util.StringJoiner;

import static io.vn.nguyenduck.blocktopograph.Constants.*;
import static io.vn.nguyenduck.blocktopograph.Logger.LOGGER;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_FOLDER_PERMISSION = 0x00a;
    private static final String ACCESS_URI = "access_uri";
    private static SharedPreferences preferences;
    private final WorldListAdapter adapter = new WorldListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getPreferences(MODE_PRIVATE);
        DocumentUtils.contentResolver = getContentResolver();

        setContentView(R.layout.world_list);

        RecyclerView view = findViewById(R.id.world_list_layout);

        view.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this,
                2, GridLayoutManager.VERTICAL, false);
        view.setLayoutManager(layoutManager);

        if (getCurrentRootFolder() != null) {
            adapter.initAdapter(getCurrentRootFolder());
        }
    }

    private DocumentFile getCurrentRootFolder() {
        if (preferences.contains(ACCESS_URI)) {
            Uri oldUri = Uri.parse(preferences.getString(ACCESS_URI, ""));
            DocumentFile f = DocumentFile.fromTreeUri(this, oldUri);
            if (f != null && f.canWrite()) return f;
        }
        return null;
    }

    private boolean hasPermission() {
        return getCurrentRootFolder() != null;
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

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        if (!hasPermission()) displayStoragePermissionAlert();
        else {
            RecyclerView view = findViewById(R.id.world_list_layout);
            if (view.getAdapter() != null) {
                ((WorldListAdapter) view.getAdapter()).initAdapter(getCurrentRootFolder(), true);
                view.requestLayout();
            }
        }
    }

    private void requestPermission() {
        Uri uri = DocumentsContract.buildDocumentUri(
                DOC_AUTHORITY,
                new StringJoiner("/")
                        .add(DOC_DATA_PATH)
                        .add(MINECRAFT_APP_ID)
                        .toString());
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
                .putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri)
                .addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                .addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityIfNeeded(intent, REQ_FOLDER_PERMISSION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (resultCode == Activity.RESULT_OK && requestCode == REQ_FOLDER_PERMISSION) {
            Uri uri = null;
            if (resultData != null) uri = resultData.getData();

            if (uri != null) {
                if (!Objects.equals(uri.getPath(), "/tree/primary:Android/data/com.mojang.minecraftpe")) return;

                getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                preferences.edit().putString(ACCESS_URI, uri.toString()).apply();
                DocumentFile f = DocumentFile.fromTreeUri(this, uri);
                if (f != null) adapter.initAdapter(f);
            }
        }
    }
}