package io.vn.nguyenduck.blocktopograph.world;

public class WorldPreLoader {

    public final String path;

    public WorldPreLoader(String path) {
        this.path = path;
    }

    public void update() {
        fetchWorldIcon();
//        fetchWorldData();
    }

    private void fetchWorldIcon() {
    }
}