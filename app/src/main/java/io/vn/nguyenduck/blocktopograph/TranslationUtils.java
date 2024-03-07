package io.vn.nguyenduck.blocktopograph;

public class TranslationUtils {
    public static int translateGamemode(int gamemode) {
        switch (gamemode) {
            case 0:
                return R.string.gamemode_survival;
            case 1:
                return R.string.gamemode_creative;
            case 2:
                return R.string.gamemode_adventure;
            case 3:
                return R.string.gamemode_spectator;
        }
        return R.string.gamemode_survival;
    }
}
