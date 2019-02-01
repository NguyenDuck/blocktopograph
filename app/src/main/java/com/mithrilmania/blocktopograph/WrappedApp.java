package com.mithrilmania.blocktopograph;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class WrappedApp extends Application implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultUEhan;

    public WrappedApp() {
        if (!BuildConfig.DEBUG) Fabric.with(this, new Crashlytics());
        mDefaultUEhan = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.e(this, throwable);
        mDefaultUEhan.uncaughtException(thread, throwable);
    }
}
