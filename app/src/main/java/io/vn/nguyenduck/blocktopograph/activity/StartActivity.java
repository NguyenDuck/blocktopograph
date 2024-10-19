package io.vn.nguyenduck.blocktopograph.activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION;
import static io.vn.nguyenduck.blocktopograph.Constants.SHIZUKU_PACKAGE_NAME;
import static io.vn.nguyenduck.blocktopograph.shell.Runner.isRooted;
import static io.vn.nguyenduck.blocktopograph.utils.Utils.isAndroid11Up;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

import io.vn.nguyenduck.blocktopograph.R;
import rikka.shizuku.Shizuku;

public class StartActivity extends AppCompatActivity {

    private final int STORAGE_PERMISSION_CODE = 0x7832;
    private final int SHIZUKU_PERMISSION_CODE = 0xbfde;

    private boolean StoragePermission = false;
    private boolean ShizukuPermission = false;
    private boolean ShizukuInstalled = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        Shizuku.addRequestPermissionResultListener(REQUEST_PERMISSION_RESULT_LISTENER);

        // setup for auto rotated screen on sensor
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        StoragePermission = StoragePermission || hasFileAccessPermission();
        ShizukuInstalled = ShizukuInstalled || hasInstalledShizuku();
        ShizukuPermission = ShizukuPermission || hasShizukuPermission();

        if (!StoragePermission) requestStoragePermission();
        if (isAndroid11Up()) {
            if (isRooted()) {
                startMainActivity();
            } else if (!ShizukuInstalled) {
                showShizukuAppNeeded();
            } else if (!Shizuku.pingBinder()) {
                showNotRunningShizuku();
            } else if (!ShizukuPermission) {
                Shizuku.requestPermission(SHIZUKU_PERMISSION_CODE);
            }
            if (StoragePermission && ShizukuPermission) {
                startMainActivity();
            }
        } else if (StoragePermission) {
            startMainActivity();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Shizuku.removeRequestPermissionResultListener(REQUEST_PERMISSION_RESULT_LISTENER);
    }

    private boolean hasInstalledShizuku() {
        try {
            getPackageManager().getPackageInfo(SHIZUKU_PACKAGE_NAME, 0);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private boolean hasShizukuPermission() {
        return Shizuku.pingBinder() && Shizuku.checkSelfPermission() == PERMISSION_GRANTED;
    }

    private boolean hasFileAccessPermission() {
        if (isAndroid11Up()) {
            return Environment.isExternalStorageManager();
        } else {
            return (checkSelfPermission(WRITE_EXTERNAL_STORAGE) |
                    checkSelfPermission(READ_EXTERNAL_STORAGE)
            ) == PERMISSION_GRANTED;
        }
    }

    private void requestStoragePermission() {
        if (isAndroid11Up()) {
            startActivityIfNeeded(
                    new Intent(ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION),
                    STORAGE_PERMISSION_CODE
            );
        } else {
            requestPermissions(
                    new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE
            );
        }
    }

    private void showShizukuAppNeeded() {
        new AlertDialog.Builder(this)
                .setTitle("Shizuku Not Installed!")
                .setMessage("You need to install shizuku first")
                .setPositiveButton("Open Google Play Store", (d, w) -> showShizukuInMarket())
                .setNegativeButton("Exit", (d, w) -> System.exit(0))
                .setCancelable(false)
                .show();
    }

    private void showNotRunningShizuku() {
        new AlertDialog.Builder(this)
                .setTitle("Shizuku Not Running!")
                .setMessage("You need to start shizuku and run it first")
                .setPositiveButton("Open Shizuku", (d, w) -> openShizuku())
                .setNegativeButton("Exit", (d, w) -> System.exit(0))
                .setCancelable(false)
                .show();
    }

    private void showShizukuInMarket() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + SHIZUKU_PACKAGE_NAME)));
        } catch (Exception ignored) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + SHIZUKU_PACKAGE_NAME)));
        }
    }

    private void openShizuku() {
        startActivity(getPackageManager().getLaunchIntentForPackage(SHIZUKU_PACKAGE_NAME));
    }

    private final Shizuku.OnRequestPermissionResultListener REQUEST_PERMISSION_RESULT_LISTENER = this::onRequestPermissionResult;

    private void onRequestPermissionResult(int requestCode, int result) {
        if (requestCode == SHIZUKU_PERMISSION_CODE)
            ShizukuPermission = result == PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            StoragePermission = grantResults.length > 0 &&
                    Arrays.stream(grantResults).allMatch(v -> v == PERMISSION_GRANTED);
        }
    }
}