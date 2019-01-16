//
// Created by barco on 2018/3/23.
//

#ifndef CONVARTER_CHUNK_H
#define CONVARTER_CHUNK_H

#include <leveldb/db.h>
#include <vector>
#include <subchunk.h>
#include <decompress_allocator.h>
#include "mapkey.h"
#include "debug_conf.h"
#include "qstr.h"

class Chunk {
private:

    //Constants.

    static const int32_t msk[];

    static const char pattern_name[];

    static const char pattern_val[];

    //Member vars.

    mapkey_t key;

    SubChunk *subchunks[16];

    void loadSubChunk(leveldb::DB *db, unsigned char which);

public:

    Chunk(leveldb::DB *db, mapkey_t key);

    ~Chunk();

    uint16_t getBlock(unsigned char x, unsigned char y, unsigned char z);
};

#endif //CONVARTER_CHUNK_H
