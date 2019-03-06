package com.mithrilmania.blocktopograph.map.marker;

import android.content.Context;
import android.widget.ImageView;

/**
 * TODO docs
 */
public class MarkerImageView extends androidx.appcompat.widget.AppCompatImageView {

    private final AbstractMarker markerHook;

    public MarkerImageView(Context context, AbstractMarker markerHook) {
        super(context);
        this.markerHook = markerHook;
    }

    public AbstractMarker getMarkerHook(){
        return markerHook;
    }

}
