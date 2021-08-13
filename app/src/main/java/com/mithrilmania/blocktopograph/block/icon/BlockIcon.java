package com.mithrilmania.blocktopograph.block.icon;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.Serializable;

public abstract class BlockIcon implements Serializable{

    abstract public Bitmap getIcon(Context context);
}
