package com.mithrilmania.blocktopograph.backup;


import androidx.annotation.NonNull;

import java.io.File;
import java.util.Date;

public class Backup {

    public String name;

    public Date time;

    public File file;

    public String size;

    public Backup(@NonNull String name, @NonNull Date time, @NonNull File file, @NonNull String size) {
        this.file = file;
        this.name = name;
        this.time = time;
        this.size = size;
    }
}