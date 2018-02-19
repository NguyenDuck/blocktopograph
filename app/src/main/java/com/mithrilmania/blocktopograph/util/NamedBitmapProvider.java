package com.mithrilmania.blocktopograph.util;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

public interface NamedBitmapProvider {

    Bitmap getBitmap();

    @NonNull
    String getBitmapDisplayName();

    @NonNull
    String getBitmapDataName();

}
