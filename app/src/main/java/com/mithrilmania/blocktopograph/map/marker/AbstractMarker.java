package com.mithrilmania.blocktopograph.map.marker;

import android.content.Context;
import androidx.annotation.NonNull;
import android.widget.ImageView;

import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.util.NamedBitmapProvider;
import com.mithrilmania.blocktopograph.util.NamedBitmapProviderHandle;
import com.mithrilmania.blocktopograph.util.math.Vector3;

public class AbstractMarker implements NamedBitmapProviderHandle {

    public final int x, y, z;
    public final Dimension dimension;

    public final NamedBitmapProvider namedBitmapProvider;

    public final boolean isCustom;

    public AbstractMarker(int x, int y, int z, Dimension dimension, NamedBitmapProvider namedBitmapProvider, boolean isCustom) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
        this.namedBitmapProvider = namedBitmapProvider;
        this.isCustom = isCustom;
    }

    public String getPositionDescription(Context context) {
        return context.getString(R.string.player_position_desc,
                Math.round(x), Math.round(y),
                Math.round(z), context.getString(dimension.getName()));
    }

    public int getChunkX() {
        return x >> 4;
    }

    public int getChunkZ() {
        return z >> 4;
    }

    public MarkerImageView view;

    public MarkerImageView getView(Context context) {
        if (view == null) {
            view = new MarkerImageView(context, this);
            this.loadIcon(view);
        }
        return view;
    }

    /**
     * Loads the provided bitmap into the image view.
     *
     * @param iconView The view to load the icon into.
     */
    public void loadIcon(ImageView iconView) {
        iconView.setImageBitmap(this.getNamedBitmapProvider().getBitmap());
    }

    @NonNull
    @Override
    public NamedBitmapProvider getNamedBitmapProvider() {
        return namedBitmapProvider;
    }

    public AbstractMarker copy(int x, int y, int z, Dimension dimension) {
        return new AbstractMarker(x, y, z, dimension, this.namedBitmapProvider, this.isCustom);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractMarker that = (AbstractMarker) o;

        return x == that.x
                && y == that.y
                && z == that.z
                && dimension == that.dimension
                && (namedBitmapProvider != null
                ? namedBitmapProvider.equals(that.namedBitmapProvider)
                : that.namedBitmapProvider == null);

    }

    @Override
    public int hashCode() {
        int result = Vector3.intHash(x, y, z);
        result = 31 * result + (dimension != null ? dimension.hashCode() : 0);
        result = 31 * result + (namedBitmapProvider != null ? namedBitmapProvider.hashCode() : 0);
        return result;
    }
}
