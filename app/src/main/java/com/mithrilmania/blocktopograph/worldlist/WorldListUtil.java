package com.mithrilmania.blocktopograph.worldlist;


import android.content.Context;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.World;

import java.text.DateFormat;

public class WorldListUtil {

    public static long getLastPlayedTimestamp(World world) {
        try {
            return (long) world.getLevel().getChildTagByKey("LastPlayed").getValue();
        } catch (Exception e) {
            Log.d(WorldListUtil.class, e);
            return 0;
        }
    }

    public static String getLastPlayedText(Context context, World world) {
        long lastPlayed = getLastPlayedTimestamp(world);
        if (lastPlayed == 0) return "?";
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);

        return dateFormat.format(lastPlayed * 1000);
    }

    public static String getWorldGamemodeText(Context context, World world) {
        String gameMode;
        try {
            int gameType = (int) world.getLevel().getChildTagByKey("GameType").getValue();
            switch (gameType) {
                case 0:
                    gameMode = context.getString(R.string.gamemode_survival);
                    break;
                case 1:
                    gameMode = context.getString(R.string.gamemode_creative);
                    break;
                case 2:
                    gameMode = context.getString(R.string.gamemode_adventure);
                    break;
                default:
                    gameMode = "?";
            }
        } catch (Exception e) {
            Log.d(WorldListUtil.class, e);
            gameMode = "??";
        }
        return gameMode;
    }


}
