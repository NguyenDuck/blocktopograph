package io.vn.nguyenduck.blocktopograph.file;

import androidx.annotation.NonNull;

import java.util.Arrays;

import io.vn.nguyenduck.blocktopograph.shell.Runner;

public class BFile {
    private String path;
    private static final String seperator = "/";

    public BFile(String path) {
        setPath(path);
    }

    public boolean isFile() throws Exception {
        return !Runner.runString("[ -f", this.path, "] && echo 0").isEmpty();
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = String.join(seperator, path.split(seperator));
    }

    private BFile[] splitPathResult(String result) {
        return Arrays.stream(result.split(seperator)).map(BFile::new).toArray(BFile[]::new);
    }

    public BFile[] listDirs() throws Exception {
        String result = Runner.runString("find", this.path + "/*", "-maxdepth", "0", "-type", "d");
        return splitPathResult(result);
    }

    public BFile[] listFiles() throws Exception {
        String result = Runner.runString("find", this.path, "-maxdepth", "1", "-type", "f");
        return splitPathResult(result);
    }

    public String read() throws Exception {
        return Runner.runString("cat", this.path);
    }

    @NonNull
    @Override
    public String toString() {
        return "BFile{" +
                "path='" + path + '\'' +
                '}';
    }
}