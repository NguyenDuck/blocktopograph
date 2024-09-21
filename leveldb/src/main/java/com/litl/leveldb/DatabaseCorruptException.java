package com.litl.leveldb;

import java.io.Serial;

public class DatabaseCorruptException extends LevelDBException {
    @Serial
    private static final long serialVersionUID = -2110293580518875321L;

    public DatabaseCorruptException() {
    }

    public DatabaseCorruptException(String error) {
        super(error);
    }
}