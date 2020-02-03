package com.mithrilmania.blocktopograph.map;

import android.content.Context;

import androidx.annotation.NonNull;

import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.util.math.DimensionVector3;


public class Player {

    public static final String LOCAL_PLAYER_NAME = "~local_player";

    private final boolean isLocal;
    private String dbName;
    private DimensionVector3<Float> position;

    private Player(boolean isLocal, String dbName) {
        this.isLocal = isLocal;
        this.dbName = dbName;
    }

    @NonNull

    public static Player localPlayer() {
        return new Player(true, LOCAL_PLAYER_NAME);
    }

    @NonNull

    public static Player networkPlayer(String dbName) {
        return new Player(false, dbName);
    }

    public boolean isLocal() {
        return isLocal;
    }

    public String getDbName() {
        return dbName;
    }

    public DimensionVector3<Float> getPosition() {
        return position;
    }

    public String getPositionDescription(Context context) {
        if (position == null)
            return context.getString(R.string.map_locator_player_pos_unknown);
        return context.getString(R.string.player_position_desc,
                Math.round(position.x), Math.round(position.y),
                Math.round(position.z), context.getString(position.dimension.getName()));
    }

    public void setPosition(DimensionVector3<Float> position) {
        this.position = position;
    }
}
