package com.mithrilmania.blocktopograph;

import android.app.Application;

public class WrappedApp extends Application implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultUEhan;

    public WrappedApp() {
        mDefaultUEhan = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.e(this, throwable);
        mDefaultUEhan.uncaughtException(thread, throwable);
    }
}
