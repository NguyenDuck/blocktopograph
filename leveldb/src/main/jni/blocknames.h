//
// Created by barco on 2018/3/29.
//

#ifndef CONVARTER_BLOCKNAMES_H
#define CONVARTER_BLOCKNAMES_H

#include "qstr.h"

class BlockNames {
public:
    static char names[256][32];

    static unsigned char resolve(qstr name);
};

#endif //CONVARTER_BLOCKNAMES_H
