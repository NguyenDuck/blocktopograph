//
// Created by barco on 2018/3/23.
//

#ifndef CONVARTER_CHUNK_H
#define CONVARTER_CHUNK_H

#include <leveldb/db.h>
#include <vector>
#include <decompress_allocator.h>
#include "mapkey.h"
#include "debug_conf.h"
#include "qstr.h"

class SubChunk;

class Chunk {
private:

    //Member vars.

    mapkey_t key;

    SubChunk *subchunks[16];

    void loadSubChunk(leveldb::DB *db, unsigned char which);

public:

    static leveldb::ReadOptions readOptions;

    Chunk(leveldb::DB *db, mapkey_t key);

    ~Chunk();

    uint16_t getBlock(unsigned char x, unsigned char y, unsigned char z);
};

#endif //CONVARTER_CHUNK_H
