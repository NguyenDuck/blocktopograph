package com.mithrilmania.blocktopograph.util;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;

public interface NamedBitmapProvider {

    Bitmap getBitmap();

    @NonNull
    String getBitmapDisplayName();

    @NonNull
    String getBitmapDataName();

}
