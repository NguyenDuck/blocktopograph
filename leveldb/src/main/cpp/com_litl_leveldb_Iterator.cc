#include <jni.h>
#include <android/log.h>

#include "leveldbjni.h"

#include "leveldb/iterator.h"

static void
nativeDestroy(JNIEnv* env,
              jclass clazz,
              jlong ptr)
{
    leveldb::Iterator* iter = reinterpret_cast<leveldb::Iterator*>(ptr);

    delete iter;
}

static void
nativeSeekToFirst(JNIEnv* env,
                  jclass clazz,
                  jlong iterPtr)
{
    leveldb::Iterator* iter = reinterpret_cast<leveldb::Iterator*>(iterPtr);
    iter->SeekToFirst();
}

static void
nativeSeekToLast(JNIEnv* env,
                  jclass clazz,
                  jlong iterPtr)
{
    leveldb::Iterator* iter = reinterpret_cast<leveldb::Iterator*>(iterPtr);
    iter->SeekToLast();
}

static void
nativeSeek(JNIEnv* env,
           jclass clazz,
           jlong iterPtr,
           jbyteArray keyObj)
{
    leveldb::Iterator* iter = reinterpret_cast<leveldb::Iterator*>(iterPtr);

    size_t keyLen = env->GetArrayLength(keyObj);
    jbyte *buffer = env->GetByteArrayElements(keyObj, NULL);

    iter->Seek(leveldb::Slice((const char *)buffer, keyLen));
    env->ReleaseByteArrayElements(keyObj, buffer, JNI_ABORT);
}

static jboolean
nativeValid(JNIEnv* env,
            jclass clazz,
            jlong iterPtr)
{
    leveldb::Iterator* iter = reinterpret_cast<leveldb::Iterator*>(iterPtr);
    return iter->Valid();
}

static void
nativeNext(JNIEnv* env,
           jclass clazz,
           jlong iterPtr)
{
    leveldb::Iterator* iter = reinterpret_cast<leveldb::Iterator*>(iterPtr);
    iter->Next();
}

static void
nativePrev(JNIEnv* env,
           jclass clazz,
           jlong iterPtr)
{
    leveldb::Iterator* iter = reinterpret_cast<leveldb::Iterator*>(iterPtr);
    iter->Prev();
}

static jbyteArray
nativeKey(JNIEnv* env,
          jclass clazz,
          jlong iterPtr)
{
    leveldb::Iterator* iter = reinterpret_cast<leveldb::Iterator*>(iterPtr);
    leveldb::Slice key = iter->key();

    size_t len = key.size();
    jbyteArray result = env->NewByteArray(len);
    env->SetByteArrayRegion(result, 0, len, (const jbyte *) key.data());
    return result;
}

static jbyteArray
nativeValue(JNIEnv* env,
            jclass clazz,
            jlong iterPtr)
{
    leveldb::Iterator* iter = reinterpret_cast<leveldb::Iterator*>(iterPtr);
    leveldb::Slice value = iter->value();

    size_t len = value.size();
    jbyteArray result = env->NewByteArray(len);
    env->SetByteArrayRegion(result, 0, len, (const jbyte *) value.data());
    return result;
}

static JNINativeMethod sMethods[] =
{
        { "nativeDestroy", "(J)V", (void*) nativeDestroy },
        { "nativeSeekToFirst", "(J)V", (void*) nativeSeekToFirst },
        { "nativeSeekToLast", "(J)V", (void*) nativeSeekToLast },
        { "nativeSeek", "(J[B)V", (void*) nativeSeek },
        { "nativeValid", "(J)Z", (void*) nativeValid },
        { "nativeNext", "(J)V", (void*) nativeNext },
        { "nativePrev", "(J)V", (void*) nativePrev },
        { "nativeKey", "(J)[B", (void*) nativeKey },
        { "nativeValue", "(J)[B", (void*) nativeValue }
};

int
register_com_litl_leveldb_Iterator(JNIEnv *env) {
    jclass clazz = env->FindClass("com/litl/leveldb/Iterator");
    if (!clazz) {
        LOGE("Can't find class com.litl.leveldb.Iterator");
        return 0;
    }

    return env->RegisterNatives(clazz, sMethods, NELEM(sMethods));
}
