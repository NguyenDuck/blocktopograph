package io.vn.nguyenduck.blocktopograph.types;

public enum GameMode {
    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2),
    SPECTATOR(3);

    private final int id;

    GameMode(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static GameMode fromId(int id) {
        for (GameMode gameMode : values()) {
            if (gameMode.getId() == id) {
                return gameMode;
            }
        }
        return null;
    }

    public static GameMode fromName(String name) {
        for (GameMode gameMode : values()) {
            if (gameMode.name().equalsIgnoreCase(name)) {
                return gameMode;
            }
        }
        return null;
    }

    public static GameMode from(Object o) {
        if (o instanceof Integer v) {
            return fromId(v);
        } else if (o instanceof String v) {
            return fromName(v);
        }
        return null;
    }
}