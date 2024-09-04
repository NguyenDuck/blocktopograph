package com.litl.leveldb;

public class LevelDBException extends RuntimeException {
    private static final long serialVersionUID = 2903013251786326801L;

    public LevelDBException() {
    }

    public LevelDBException(String error) {
        super(error);
    }

    public LevelDBException(String error, Throwable cause) {
        super(error, cause);
    }
}
