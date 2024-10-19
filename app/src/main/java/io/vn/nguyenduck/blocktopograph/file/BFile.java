package io.vn.nguyenduck.blocktopograph.file;

import java.io.File;
import java.util.Arrays;

import io.vn.nguyenduck.blocktopograph.shell.Runner;

public class BFile extends File {
    private String path;
    private static final String seperator = "/";

    public BFile(String path) {
        super(path);
        setPath(path);
    }

    public boolean isFile() {
        String command = String.format("[ -f %s ] && echo 0", path);
        return !Runner.runString(command).isEmpty();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = String.join(seperator, path.split(seperator));
    }

    private BFile[] splitPathResult(String result) {
        return Arrays.stream(result.split(seperator)).map(BFile::new).toArray(BFile[]::new);
    }

    public BFile[] listDirs() {
        String command = String.format("find %s/* -maxdepth 1 -type d", path);
        return splitPathResult(Runner.runString(command));
    }

    public BFile[] listFiles() {
        String command = String.format("find %s -maxdepth 1 -type f", path);
        return splitPathResult(Runner.runString(command));
    }

    public String read() {
        return Runner.runString("cat", path);
    }

    public long lastModified() {
        return Long.parseLong(Runner.runString("stat", "-c", "%Y", path));
    }

    public void copyTo(BFile dest) {
        if (new BFile(dest.getPath() + getName()).exists()) {
            Runner.run("cp", path, dest.getPath());
        } else {
            Runner.run("cp", "-r", path, dest.getPath());
        }
    }
}