#include <string.h>
#include <jni.h>

#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>

#include "leveldbjni.h"

#include "leveldb/db.h"
#include "leveldb/write_batch.h"
#include "leveldb/zlib_compressor.h"
#include "leveldb/filter_policy.h"
#include "leveldb/cache.h"
#include "leveldb/options.h"
#include "leveldb/decompress_allocator.h"
#include "leveldb/env.h"

static jmethodID gByteBuffer_isDirectMethodID;
static jmethodID gByteBuffer_positionMethodID;
static jmethodID gByteBuffer_limitMethodID;
static jmethodID gByteBuffer_arrayMethodID;

static leveldb::ZlibCompressor *zlibCompressorInstance;
static leveldb::ZlibCompressorRaw *zlibCompressorRawInstance;
static leveldb::Logger *nullLoggerInstance;
static leveldb::DecompressAllocator *decompressAllocatorInstance;

class NullLogger : public leveldb::Logger {
public:
    void Logv(const char *, va_list) override {
    }
};

static void initDb(JNIEnv *env) {
    static bool gInited;

    if (!gInited) {
        jclass byteBuffer_Clazz = env->FindClass("java/nio/ByteBuffer");
        gByteBuffer_isDirectMethodID = env->GetMethodID(byteBuffer_Clazz,
                                                        "isDirect", "()Z");
        gByteBuffer_positionMethodID = env->GetMethodID(byteBuffer_Clazz,
                                                        "position", "()I");
        gByteBuffer_limitMethodID = env->GetMethodID(byteBuffer_Clazz,
                                                     "limit", "()I");
        gByteBuffer_arrayMethodID = env->GetMethodID(byteBuffer_Clazz,
                                                     "array", "()[B");
        gInited = true;
    }
}

static jlong
nativeOpen(JNIEnv *env,
           jclass clazz,
           jstring dbpath) {

    initDb(env);

    const char *path = env->GetStringUTFChars(dbpath, 0);
    LOGI("Opening database %s", path);

    leveldb::DB *db;
    leveldb::Options options;
    options.create_if_missing = true;
    options.paranoid_checks = true;
    if (zlibCompressorInstance == nullptr)
        zlibCompressorInstance = new leveldb::ZlibCompressor();
    if (zlibCompressorRawInstance == nullptr)
        zlibCompressorRawInstance = new leveldb::ZlibCompressorRaw(-1);
    if (nullLoggerInstance == nullptr)
        nullLoggerInstance = new NullLogger();
    if (decompressAllocatorInstance == nullptr)
        decompressAllocatorInstance = new leveldb::DecompressAllocator();

    //create a bloom filter to quickly tell if a key is in the database or not
    options.filter_policy = leveldb::NewBloomFilterPolicy(10);

    //create a 40 mb cache (we use this on ~1gb devices)
    options.block_cache = leveldb::NewLRUCache(40 * 1024 * 1024);

    //create a 4mb write buffer, to improve compression and touch the disk less
    options.write_buffer_size = 4 * 1024 * 1024;

    //disable internal logging. The default logger will still print out things to a file
    options.info_log = nullLoggerInstance;

    //use the new raw-zip compressor to write (and read)
    options.compressors[0] = zlibCompressorRawInstance;

    options.compressors[1] = zlibCompressorInstance;

    leveldb::Status status = leveldb::DB::Open(options, path, &db);
    env->ReleaseStringUTFChars(dbpath, path);

    if (!status.ok()) {
        throwException(env, status);
    } else {
        LOGI("Opened database");
    }

    return reinterpret_cast<jlong>(db);
}

static jstring nativeFixLdb(JNIEnv *env,
                            jclass clazz,
                            jstring dbpath) {

    initDb(env);

    const char *path = env->GetStringUTFChars(dbpath, 0);
    LOGI("Fixing database %s", path);

    leveldb::DB *db = nullptr;
    leveldb::Options options;
    options.create_if_missing = false;
    options.paranoid_checks = true;
    if (zlibCompressorInstance == nullptr)
        zlibCompressorInstance = new leveldb::ZlibCompressor();
    if (zlibCompressorRawInstance == nullptr)
        zlibCompressorRawInstance = new leveldb::ZlibCompressorRaw(-1);
    if (nullLoggerInstance == nullptr)
        nullLoggerInstance = new NullLogger();
    if (decompressAllocatorInstance == nullptr)
        decompressAllocatorInstance = new leveldb::DecompressAllocator();

    //create a bloom filter to quickly tell if a key is in the database or not
    options.filter_policy = leveldb::NewBloomFilterPolicy(10);

    //create a 40 mb cache (we use this on ~1gb devices)
    options.block_cache = leveldb::NewLRUCache(40 * 1024 * 1024);

    //create a 4mb write buffer, to improve compression and touch the disk less
    options.write_buffer_size = 4 * 1024 * 1024;

    //disable internal logging. The default logger will still print out things to a file
    options.info_log = nullLoggerInstance;

    //use the new raw-zip compressor to write (and read)
    options.compressors[0] = zlibCompressorRawInstance;

    options.compressors[1] = zlibCompressorInstance;

    leveldb::Status status = leveldb::RepairDB(path, options);
    env->ReleaseStringUTFChars(dbpath, path);

    delete db;

    return env->NewStringUTF(status.ToString().c_str());
}

static void
nativeClose(JNIEnv *env,
            jclass clazz,
            jlong dbPtr) {
    auto *db = reinterpret_cast<leveldb::DB *>(dbPtr);
    delete db;

    LOGI("Database closed");
}

static jbyteArray
nativeGet(JNIEnv *env,
          jclass clazz,
          jlong dbPtr,
          jlong snapshotPtr,
          jbyteArray keyObj) {
    auto *db = reinterpret_cast<leveldb::DB *>(dbPtr);
    leveldb::ReadOptions options = leveldb::ReadOptions();
    options.decompress_allocator = decompressAllocatorInstance;
    //options.snapshot = reinterpret_cast<leveldb::Snapshot *>(snapshotPtr);

    size_t keyLen = static_cast<size_t>(env->GetArrayLength(keyObj));
    jbyte *buffer = env->GetByteArrayElements(keyObj, NULL);
    jbyteArray result;

    leveldb::Slice key = leveldb::Slice((const char *) buffer, keyLen);
    //leveldb::Iterator *iter = db->NewIterator(options);
    //iter->Seek(key);
    //if (iter->Valid() && key == iter->key()) {
    //leveldb::Slice value = iter->value();
    std::string str;
    leveldb::Status status = db->Get(options, key, &str);
    env->ReleaseByteArrayElements(keyObj, buffer, JNI_ABORT);
    if (status.ok()) {
        size_t len = str.size();
        result = env->NewByteArray(static_cast<jsize>(len));
        env->SetByteArrayRegion(result, 0, static_cast<jsize>(len), (const jbyte *) str.c_str());
    } else if (status.IsNotFound())result = NULL;
    else {
        throwException(env, status);
        result = NULL;
    }

    //} else {
    //result = NULL;
    //}
    //delete iter;

    return result;
}

static jbyteArray
nativeGetBB(JNIEnv *env,
            jclass clazz,
            jlong dbPtr,
            jlong snapshotPtr,
            jobject keyObj) {
    leveldb::DB *db = reinterpret_cast<leveldb::DB *>(dbPtr);
    leveldb::ReadOptions options = leveldb::ReadOptions();
    options.snapshot = reinterpret_cast<leveldb::Snapshot *>(snapshotPtr);

    jint keyPos = env->CallIntMethod(keyObj, gByteBuffer_positionMethodID);
    jint keyLimit = env->CallIntMethod(keyObj, gByteBuffer_limitMethodID);
    jboolean keyIsDirect = env->CallBooleanMethod(keyObj, gByteBuffer_isDirectMethodID);
    jbyteArray keyArray;
    void *key;
    if (keyIsDirect) {
        key = env->GetDirectBufferAddress(keyObj);
        keyArray = NULL;
    } else {
        keyArray = (jbyteArray) env->CallObjectMethod(keyObj, gByteBuffer_arrayMethodID);
        key = (void *) env->GetByteArrayElements(keyArray, NULL);
    }

    jbyteArray result;
    leveldb::Slice keySlice = leveldb::Slice((const char *) key + keyPos,
                                             static_cast<size_t>(keyLimit - keyPos));
    leveldb::Iterator *iter = db->NewIterator(options);
    iter->Seek(keySlice);
    if (iter->Valid() && keySlice == iter->key()) {
        leveldb::Slice value = iter->value();
        size_t len = value.size();
        result = env->NewByteArray(static_cast<jsize>(len));
        env->SetByteArrayRegion(result, 0, static_cast<jsize>(len), (const jbyte *) value.data());
    } else {
        result = NULL;
    }

    if (keyArray) {
        env->ReleaseByteArrayElements(keyArray, (jbyte *) key, JNI_ABORT);
    }

    delete iter;

    return result;
}

static void
nativePut(JNIEnv *env,
          jclass clazz,
          jlong dbPtr,
          jbyteArray keyObj,
          jbyteArray valObj) {
    leveldb::DB *db = reinterpret_cast<leveldb::DB *>(dbPtr);

    size_t keyLen = env->GetArrayLength(keyObj);
    jbyte *keyBuf = env->GetByteArrayElements(keyObj, NULL);

    size_t valLen = env->GetArrayLength(valObj);
    jbyte *valBuf = env->GetByteArrayElements(valObj, NULL);

    leveldb::Status status = db->Put(leveldb::WriteOptions(),
                                     leveldb::Slice((const char *) keyBuf, keyLen),
                                     leveldb::Slice((const char *) valBuf, valLen));

    env->ReleaseByteArrayElements(keyObj, keyBuf, JNI_ABORT);
    env->ReleaseByteArrayElements(valObj, valBuf, JNI_ABORT);

    if (!status.ok()) {
        throwException(env, status);
    }
}

static void
nativeDelete(JNIEnv *env,
             jclass clazz,
             jlong dbPtr,
             jbyteArray keyObj) {
    leveldb::DB *db = reinterpret_cast<leveldb::DB *>(dbPtr);

    size_t keyLen = env->GetArrayLength(keyObj);
    jbyte *buffer = env->GetByteArrayElements(keyObj, NULL);

    leveldb::Status status = db->Delete(leveldb::WriteOptions(),
                                        leveldb::Slice((const char *) buffer, keyLen));
    env->ReleaseByteArrayElements(keyObj, buffer, JNI_ABORT);

    if (!status.ok()) {
        throwException(env, status);
    }
}

static void
nativeWrite(JNIEnv *env,
            jclass clazz,
            jlong dbPtr,
            jlong batchPtr) {
    leveldb::DB *db = reinterpret_cast<leveldb::DB *>(dbPtr);

    leveldb::WriteBatch *batch = (leveldb::WriteBatch *) batchPtr;
    leveldb::Status status = db->Write(leveldb::WriteOptions(), batch);
    if (!status.ok()) {
        throwException(env, status);
    }
}

static jlong
nativeIterator(JNIEnv *env,
               jclass clazz,
               jlong dbPtr,
               jlong snapshotPtr) {
    leveldb::DB *db = reinterpret_cast<leveldb::DB *>(dbPtr);
    leveldb::ReadOptions options = leveldb::ReadOptions();
    options.snapshot = reinterpret_cast<leveldb::Snapshot *>(snapshotPtr);

    leveldb::Iterator *iter = db->NewIterator(options);
    return reinterpret_cast<jlong>(iter);
}

static jlong
nativeGetSnapshot(JNIEnv *env,
                  jclass clazz,
                  jlong dbPtr) {
    leveldb::DB *db = reinterpret_cast<leveldb::DB *>(dbPtr);
    const leveldb::Snapshot *snapshot = db->GetSnapshot();
    return reinterpret_cast<jlong>(snapshot);
}

static void
nativeReleaseSnapshot(JNIEnv *env,
                      jclass clazz,
                      jlong dbPtr,
                      jlong snapshotPtr) {
    leveldb::DB *db = reinterpret_cast<leveldb::DB *>(dbPtr);
    const leveldb::Snapshot *snapshot = reinterpret_cast<leveldb::Snapshot *>(snapshotPtr);
    db->ReleaseSnapshot(snapshot);
}

static void
nativeDestroy(JNIEnv *env,
              jclass clazz,
              jstring dbpath) {
    const char *path = env->GetStringUTFChars(dbpath, 0);
    leveldb::Options options;
    options.create_if_missing = true;
    leveldb::Status status = DestroyDB(path, options);
    if (!status.ok()) {
        throwException(env, status);
    }
}

static JNINativeMethod sMethods[] =
    {
        {"nativeOpen",   "(Ljava/lang/String;)J",       (void *) nativeOpen},
        {"fixLdb",       "(Ljava/lang/String;)"
                         "Ljava/lang/String;",          (void *) nativeFixLdb},
        {"nativeClose",  "(J)V",                        (void *) nativeClose},
        {"nativeGet",    "(JJ[B)[B",                    (void *) nativeGet},
        {"nativeGet",    "(JJLjava/nio/ByteBuffer;)[B", (void *) nativeGetBB},
        {"nativePut",    "(J[B[B)V",                    (void *) nativePut},
        {"nativeDelete", "(J[B)V",                      (void *) nativeDelete},
        {"nativeWrite",  "(JJ)V",                       (void *) nativeWrite},
        {"nativeIterator",        "(JJ)J",                       (void *) nativeIterator},
        {"nativeGetSnapshot",     "(J)J",                        (void *) nativeGetSnapshot},
        {"nativeReleaseSnapshot", "(JJ)V",                       (void *) nativeReleaseSnapshot},
        {"nativeDestroy",         "(Ljava/lang/String;)V",       (void *) nativeDestroy}
    };

int
register_com_litl_leveldb_DB(JNIEnv *env) {
    jclass clazz = env->FindClass("com/litl/leveldb/DB");
    if (!clazz) {
        LOGE("Can't find class com.litl.leveldb.DB");
        return 0;
    }

    return env->RegisterNatives(clazz, sMethods, NELEM(sMethods));
}
