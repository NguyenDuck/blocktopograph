//
// Created by barco on 2018/3/24.
//

#ifndef CONVARTER_QSTR_H
#define CONVARTER_QSTR_H

typedef struct {
    unsigned int length;
    char *str;
} qstr;

typedef struct {
    unsigned int length;
    const char *str;
} qcstr;

typedef struct {
    unsigned int length;
    unsigned char *str;
} qustr;

#endif //CONVARTER_QSTR_H
