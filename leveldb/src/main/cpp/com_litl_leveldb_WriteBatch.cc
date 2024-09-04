#include <jni.h>

#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>

#include "leveldbjni.h"

#include "leveldb/write_batch.h"

static jmethodID gByteBuffer_isDirectMethodID;
static jmethodID gByteBuffer_positionMethodID;
static jmethodID gByteBuffer_limitMethodID;
static jmethodID gByteBuffer_arrayMethodID;

static jlong
nativeCreate(JNIEnv* env,
             jclass clazz)
{
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

    leveldb::WriteBatch* batch = new leveldb::WriteBatch();
    return reinterpret_cast<jlong>(batch);
}

static void
nativeDestroy(JNIEnv* env,
              jclass clazz,
              jlong ptr)
{
    leveldb::WriteBatch* batch = reinterpret_cast<leveldb::WriteBatch*>(ptr);

    delete batch;
}

static void
nativeDelete(JNIEnv* env,
             jclass clazz,
             jlong ptr,
             jobject buffer)
{
    leveldb::WriteBatch* batch = reinterpret_cast<leveldb::WriteBatch*>(ptr);

    jint pos = env->CallIntMethod(buffer, gByteBuffer_positionMethodID);
    jint limit = env->CallIntMethod(buffer, gByteBuffer_limitMethodID);
    jboolean isDirect = env->CallBooleanMethod(buffer, gByteBuffer_isDirectMethodID);
    if (isDirect) {
        const char *bytes = (const char *) env->GetDirectBufferAddress(buffer);
        batch->Delete(leveldb::Slice(bytes + pos, limit - pos));
    } else {
        jbyteArray array = (jbyteArray) env->CallObjectMethod(buffer, gByteBuffer_arrayMethodID);
        jbyte *bytes = env->GetByteArrayElements(array, NULL);
        batch->Delete(leveldb::Slice((const char *) bytes + pos, limit - pos));
        env->ReleaseByteArrayElements(array, bytes, JNI_ABORT);
    }
}

static void
nativePut(JNIEnv* env,
          jclass clazz,
          jlong ptr,
          jobject keyObj,
          jobject valObj)
{
    leveldb::WriteBatch* batch = reinterpret_cast<leveldb::WriteBatch*>(ptr);

    jint keyPos = env->CallIntMethod(keyObj, gByteBuffer_positionMethodID);
    jint keyLimit = env->CallIntMethod(keyObj, gByteBuffer_limitMethodID);
    jboolean keyIsDirect = env->CallBooleanMethod(keyObj, gByteBuffer_isDirectMethodID);
    jbyteArray keyArray;
    void* key;
    if (keyIsDirect) {
        key = env->GetDirectBufferAddress(keyObj);
        keyArray = NULL;
    } else {
        keyArray = (jbyteArray) env->CallObjectMethod(keyObj, gByteBuffer_arrayMethodID);
        key = (void*) env->GetByteArrayElements(keyArray, NULL);
    }

    jint valPos = env->CallIntMethod(valObj, gByteBuffer_positionMethodID);
    jint valLimit = env->CallIntMethod(valObj, gByteBuffer_limitMethodID);
    jboolean valIsDirect = env->CallBooleanMethod(valObj, gByteBuffer_isDirectMethodID);
    jbyteArray valArray;
    void* val;
    if (valIsDirect) {
        val = env->GetDirectBufferAddress(valObj);
        valArray = NULL;
    } else {
        valArray = (jbyteArray) env->CallObjectMethod(valObj, gByteBuffer_arrayMethodID);
        val = (void*) env->GetByteArrayElements(valArray, NULL);
    }

    batch->Put(leveldb::Slice((const char *) key + keyPos, keyLimit - keyPos),
               leveldb::Slice((const char *) val + valPos, valLimit - valPos));

    if (keyArray) {
        env->ReleaseByteArrayElements(keyArray, (jbyte*) key, JNI_ABORT);
    }
    if (valArray) {
        env->ReleaseByteArrayElements(valArray, (jbyte*) val, JNI_ABORT);
    }
}

static void
nativeClear(JNIEnv* env,
            jclass clazz,
            jlong ptr)
{
    leveldb::WriteBatch* batch = reinterpret_cast<leveldb::WriteBatch*>(ptr);
    batch->Clear();
}

static JNINativeMethod sMethods[] =
{
        { "nativeCreate", "()J", (void*) nativeCreate },
        { "nativeDestroy", "(J)V", (void*) nativeDestroy },
        { "nativeDelete", "(JLjava/nio/ByteBuffer;)V", (void*) nativeDelete },
        { "nativePut", "(JLjava/nio/ByteBuffer;Ljava/nio/ByteBuffer;)V", (void*) nativePut },
        { "nativeClear", "(J)V", (void*) nativeClear }
};

int
register_com_litl_leveldb_WriteBatch(JNIEnv *env) {
    jclass clazz = env->FindClass("com/litl/leveldb/WriteBatch");
    if (!clazz) {
        LOGE("Can't find class com.litl.leveldb.WriteBatch");
        return 0;
    }

    return env->RegisterNatives(clazz, sMethods, NELEM(sMethods));
}
