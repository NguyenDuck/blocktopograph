//
// Created by barco on 2018/3/27.
//

#ifndef CONVARTER_MAPKEY_H
#define CONVARTER_MAPKEY_H

#include <cstdint>

typedef struct {
    int32_t x_div16;
    int32_t z_div16;
    int32_t dimension;
} mapkey_t;

#define LDBKEY_STRUCT(x, z, dim) mapkey_t{x >> 4, z >> 4, dim}

#define LDBKEY_SUBCHUNK(k, ydiv) \
    char key[14];\
    if(true){\
        char* ptr = key;\
        *(int32_t*)ptr = k.x_div16;\
        ptr+=4;\
        *(int32_t*)ptr = k.z_div16;\
        ptr += 4;\
        if(k.dimension != 0){\
            *(int32_t*)ptr = k.dimension;\
            ptr += 4;\
        }\
        *ptr = 0x2f;\
        ptr++;\
        *ptr = ydiv;\
    }

#define LDBKEY_VERSION(k) \
    char key_db[14];\
    if(true){\
        char* ptr = key_db;\
        *(int32_t*)ptr = k.x_div16;\
        ptr+=4;\
        *(int32_t*)ptr = k.z_div16;\
        ptr += 4;\
        if(k.dimension != 0){\
            *(int32_t*)ptr = k.dimension;\
            ptr += 4;\
        }\
        *ptr = 0x76;\
        ptr++;\
    }

#endif //CONVARTER_MAPKEY_H
