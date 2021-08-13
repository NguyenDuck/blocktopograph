package com.mithrilmania.blocktopograph.block.icon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.block.KnownBlockRepr;

import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;

public class TexPathBlockIcon extends BlockIcon {

    private final String texPath;

    private transient WeakReference<Bitmap> texture;

    public TexPathBlockIcon(String texPath) {
        this.texPath = texPath;
        texture = null;
    }

    public Bitmap getIcon(Context context) {
        var result = texture == null ? null : texture.get();
        if (result == null) {
            result = loadIcon(context);
            if (result != null) texture = new WeakReference<>(result);
        }
        return result;
    }

    private Bitmap loadIcon(Context context) {
        try {
            return Bitmap.createScaledBitmap(
                    BitmapFactory.decodeStream(
                            context.getAssets().open(texPath)), 120, 120, false);
        } catch (FileNotFoundException e) {
            //TODO file-paths were generated from block names; some do not actually exist...
        } catch (Exception e) {
            Log.d(KnownBlockRepr.class, e);
        }
        return null;
    }
}
