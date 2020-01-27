package com.mithrilmania.blocktopograph;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;

public class WrappedApp extends Application implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultUEhan;

    private boolean mHasUnsatisfiedLinkErrorActivity;

    public WrappedApp() {
        mDefaultUEhan = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        Log.e(this, throwable);
        if (throwable instanceof UnsatisfiedLinkError) {
            if (!mHasUnsatisfiedLinkErrorActivity) {
                Intent intent = new Intent(this, UnsatisfiedLinkErrorActivity.class);
                mHasUnsatisfiedLinkErrorActivity = true;
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
        mDefaultUEhan.uncaughtException(thread, throwable);
    }
}
