package com.mithrilmania.blocktopograph;

public class Log {

    //TODO This is kind of lazy, but repeating the Log.d(*msg*) everywhere is obnoxious
    //TODO log only if debug mode is on?

    public static final String LOG_TAG = "Blocktopograph";

    public static void i(String msg){
        android.util.Log.i(LOG_TAG, msg);
    }

    public static void d(String msg){
        android.util.Log.d(LOG_TAG, msg);
    }

    public static void w(String msg){
        android.util.Log.w(LOG_TAG, msg);
    }

    public static void e(String msg){
        android.util.Log.e(LOG_TAG, msg);
    }

}
