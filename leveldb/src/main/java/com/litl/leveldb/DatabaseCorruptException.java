package com.litl.leveldb;

public class DatabaseCorruptException extends LevelDBException {
    private static final long serialVersionUID = -2110293580518875321L;

    public DatabaseCorruptException() {
    }

    public DatabaseCorruptException(String error) {
        super(error);
    }
}
