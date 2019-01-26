package com.mithrilmania.blocktopograph;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Log {

    //TODO This is kind of lazy, but repeating the Log.d(*msg*) everywhere is obnoxious
    //TODO log only if debug mode is on?

    private static final String LOG_TAG = "Blocktopo";

    private static FirebaseAnalytics mFirebaseAnalytics;

    public static void i(@NonNull String msg) {
        android.util.Log.i(LOG_TAG, msg);
    }

    public static void d(@NonNull String msg) {
        android.util.Log.d(LOG_TAG, msg);
    }

    public static void w(@NonNull String msg) {
        android.util.Log.w(LOG_TAG, msg);
    }

    public static void e(@NonNull String msg) {
        android.util.Log.e(LOG_TAG, msg);
    }

    synchronized static public FirebaseAnalytics getFirebaseAnalytics(@NonNull Context context) {
        if (mFirebaseAnalytics == null) {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

            //don't measure the test devices in analytics
            mFirebaseAnalytics.setAnalyticsCollectionEnabled(!BuildConfig.DEBUG);
        }
        return mFirebaseAnalytics;
    }

    public static void logFirebaseEvent(@NonNull Context context, @NonNull CustomFirebaseEvent firebaseEvent) {
        getFirebaseAnalytics(context).logEvent(firebaseEvent.eventID, new Bundle());
    }

    public static void logFirebaseEvent(@NonNull Context context, @NonNull CustomFirebaseEvent firebaseEvent, @NonNull Bundle eventContent) {
        getFirebaseAnalytics(context).logEvent(firebaseEvent.eventID, eventContent);
    }

    // Firebase events, these are meant to be as anonymous as possible,
    //  pure counters for usage analytics.
    // Do not remove! Removing analytics in a fork skews the results to the original userbase!
    // Forks should stay in touch, all new features are welcome.
    //Wonder why you put these things in a certain Activity.
    //That should be global.
    public enum CustomFirebaseEvent {

        //max 32 chars:     "0123456789abcdef0123456789abcdef"
        MAPFRAGMENT_OPEN("map_fragment_open"),
        MAPFRAGMENT_RESUME("map_fragment_resume"),
        MAPFRAGMENT_RESET("map_fragment_reset"),
        NBT_EDITOR_OPEN("nbt_editor_open"),
        NBT_EDITOR_SAVE("nbt_editor_save"),
        WORLD_OPEN("world_open"),
        WORLD_RESUME("world_resume"),
        GPS_PLAYER("gps_player"),
        GPS_MULTIPLAYER("gps_multiplayer"),
        GPS_SPAWN("gps_spawn"),
        GPS_MARKER("gps_marker"),
        GPS_COORD("gps_coord");

        public final String eventID;

        CustomFirebaseEvent(String eventID) {
            this.eventID = eventID;
        }
    }

}
