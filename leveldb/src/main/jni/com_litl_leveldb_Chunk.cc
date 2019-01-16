#include <string.h>
#include <jni.h>

#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>
#include <db.h>

#include "leveldbjni.h"
#include "Chunk.h"

static jlong nativeOpen(JNIEnv *env, jclass clazz, jlong dbptr, jint x, jint z, jint dim) {
    leveldb::DB *db = reinterpret_cast<leveldb::DB *>(dbptr);
    Chunk *chunk = new Chunk(db, LDBKEY_STRUCT(x, z, dim));
    return reinterpret_cast<jlong>(chunk);
}

static jint nativeGetBlock(JNIEnv *env, jclass clazz, jlong ckptr, jint x, jint y, jint z) {
    Chunk *chunk = reinterpret_cast<Chunk *>(ckptr);
    return chunk->getBlock(static_cast<unsigned char>(x), static_cast<unsigned char>(y),
                           static_cast<unsigned char>(z));
}

static void nativeClose(JNIEnv *env, jclass clazz, jlong ckptr) {
    Chunk *chunk = reinterpret_cast<Chunk *>(ckptr);
    delete chunk;
}

static JNINativeMethod sMethods[] =
    {
        {"nativeOpen",     "(JIII)J", (void *) nativeOpen},
        {"nativeGetBlock", "(JIII)I", (void *) nativeGetBlock},
        {"nativeClose",    "(J)V",    (void *) nativeClose}
    };

int
register_com_litl_leveldb_Chunk(JNIEnv *env) {
    jclass clazz = env->FindClass("com/litl/leveldb/Chunk");
    if (!clazz) {
        LOGE("Can't find class com.litl.leveldb.Chunk");
        return 0;
    }

    return env->RegisterNatives(clazz, sMethods, NELEM(sMethods));
}
