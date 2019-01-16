//
// Created by barco on 2018/12/24.
//

#ifndef CONVARTER_SUBCHUNK_H
#define CONVARTER_SUBCHUNK_H

#include <cstdint>
#include <string>
#include <slice.h>

#define DEBUG_SUBCHUNK

struct BlockStorage {
    uint8_t blen;//Length per block
    uint16_t types;//Types of blocks
    uint16_t *palette;//Palette
    uint32_t *storage;//Storage
};

class SubChunk {
private:

    static const int32_t msk[15];

    static char pattern_name[10];

    static char pattern_val[6];

    BlockStorage storages[1];

    const char *loadStorage(const char *ptr, const char *max, int which);

    uint16_t getBlockCode(unsigned char x, unsigned char y, unsigned char z, uint8_t which);

public:
    SubChunk(const std::string &buf, bool hasMultiStorage);

    ~SubChunk();

    uint16_t getBlock(unsigned char x, unsigned char y, unsigned char z);

    uint16_t
    getBlock3(unsigned char x, unsigned char y, unsigned char z, unsigned char layer);

};

#endif //CONVARTER_SUBCHUNK_H
