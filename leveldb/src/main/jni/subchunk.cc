//
// Created by barco on 2018/12/26.
//

#include <string>
#include <subchunk.h>
#include <qstr.h>
#include <blocknames.h>
#include <Chunk.h>

////////////////
//

char SubChunk::pattern_name[] = {0x0a, 0x00, 0x00, 0x08, 0x04, 0x00, 'n', 'a', 'm',
                                 'e'};
char SubChunk::pattern_val[] = {0x02, 0x03, 0x00, 'v', 'a', 'l'};

const int32_t SubChunk::msk[] = {0b1, 0b11, 0b111, 0b1111, 0b11111, 0b111111, 0b1111111,
                                 0b11111111,
                                 0b111111111, 0b1111111111, 0b11111111111,
                                 0b111111111111,
                                 0b1111111111111, 0b11111111111111, 0b11111111111111};

SubChunk::SubChunk(const std::string &buf, bool hasMultiStorage) {

    storages[0].storage = nullptr;
    storages[0].palette = nullptr;

    const char *cbuf = buf.c_str();
    const char *ptr = cbuf + 1;
    if (hasMultiStorage) {
        ptr++;
        loadStorage(ptr, cbuf + buf.length(), 0);
    } else {
        loadStorage(ptr, cbuf + buf.length(), 0);
    }
}

SubChunk::~SubChunk() {
    delete[] storages[0].storage;
    delete[] storages[0].palette;
}

const char *SubChunk::loadStorage(const char *ptr, const char *max, int which) {

#ifdef DEBUG_SUBCHUNK
    //lowest bit should be 0.
    if (((*ptr) & 1) != 0 || max - ptr < 2) {
        //
    }
#endif

    //Length of each block, in bits.
    storages[which].blen = static_cast<uint8_t>(*ptr >> 1);

    ptr++;

    //How many uint32s do it need to store all blocks?
    div_t res = div(4096, (32 / storages[which].blen));
    size_t bufsize = static_cast<size_t>(res.quot);
    if (res.rem != 0)bufsize++;

#ifdef DEBUG_SUBCHUNK
    if (max - ptr < (bufsize << 2)) {
        //
    }
#endif

    //Copy 'em up. bufsize is 4-bytes long and memcpy requires count of bytes so x4.
    storages[which].storage = new uint32_t[bufsize];
    memcpy(storages[which].storage, ptr, bufsize << 2);

    //Move the pointer to the end of uint32s.
    ptr += bufsize << 2;

    //Here records how many types of blocks are in this subchunk.
    storages[which].types = *(uint16_t *) ptr;
    ptr += 4;

    storages[which].palette = new uint16_t[storages[which].types];

    for (uint16_t i = 0; i < storages[which].types; i++) {
#ifdef  DEBUG_SUBCHUNK
        if (max - ptr < sizeof(pattern_name)) {
            //
        }
        if (memcmp(ptr, pattern_name, sizeof(pattern_name)) != 0) {
            //Something has gone wrong.
        }
#endif
        ptr += sizeof(pattern_name);
        qstr name;
#ifdef  DEBUG_SUBCHUNK
        if (max - ptr < 2) {
            //
        }
#endif
        name.length = *(uint16_t *) ptr;
        ptr += 2;
#ifdef DEBUG_SUBCHUNK
        if (max - ptr < name.length) {
            //
        }
#endif
        name.str = new char[name.length];
        memcpy(name.str, ptr, name.length);
        ptr += name.length;
#ifdef DEBUG_SUBCHUNK
        if (max - ptr < sizeof(pattern_val) + 3) {
            //
        }
        if (memcmp(ptr, pattern_val, sizeof(pattern_val)) != 0) {
            //
        }
#endif
        ptr += sizeof(pattern_val);
        storages[which].palette[i] = BlockNames::resolve(name);
        delete[] name.str;
        storages[which].palette[i] <<= 8;
        storages[which].palette[i] |= *ptr;
        ptr += 3;
    }
    return ptr;
}

uint16_t
SubChunk::getBlockCode(unsigned char x, unsigned char y, unsigned char z, uint8_t which) {

    //If there's only one storage than getBlockCode or other storages can just return 0.
    if (which == 0 && storages[which].storage == nullptr)return 0;

    BlockStorage &thiz = storages[which];

    //Get the index among all blocks.
    int index = x;
    index <<= 4;
    index |= z;
    index <<= 4;
    index |= y;

    //How many blox can each stick hold.
    int capa = (32 / thiz.blen);

    //Stick that hold this block.
    uint32_t stick = *(thiz.storage + (index / capa));

    //The bits for this block is index in palette.
    //No need care about endian but not very efficiency, i guess?
    uint32_t ind = (stick >> (index % capa * thiz.blen)) & msk[thiz.blen - 1];

    //Return the record.
    return thiz.palette[ind];
}

uint16_t SubChunk::getBlock(unsigned char x, unsigned char y, unsigned char z) {
    return getBlockCode(x, y, z, 0);
}

uint16_t SubChunk::getBlock3(unsigned char x, unsigned char y, unsigned char z,
                             unsigned char layer) {
    if (layer != 0 && layer != 1)return 0;
    return getBlockCode(x, y, z, layer);
}
