package com.litl.leveldb;

import java.io.Serial;

public class NotFoundException extends LevelDBException {
    @Serial
    private static final long serialVersionUID = 6207999645579440001L;

    public NotFoundException() {
    }

    public NotFoundException(String error) {
        super(error);
    }
}