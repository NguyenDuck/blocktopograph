#include "leveldbjni.h"

extern int register_com_litl_leveldb_DB(JNIEnv *env);

extern int register_com_litl_leveldb_WriteBatch(JNIEnv *env);

extern int register_com_litl_leveldb_Iterator(JNIEnv *env);

jint
throwException(JNIEnv *env, leveldb::Status status) {
    const char *exceptionClass;

    if (status.IsNotFound()) {
        exceptionClass = "com/litl/leveldb/NotFoundException";
    } else if (status.IsCorruption()) {
        exceptionClass = "com/litl/leveldb/DatabaseCorruptException";
    } else if (status.IsIOError()) {
        exceptionClass = "java/io/IOException";
    } else {
        return 0;
    }

    jclass clazz = env->FindClass(exceptionClass);
    if (!clazz) {
        LOGE("Can't find exception class %s", exceptionClass);
        return -1;
    }

    return env->ThrowNew(clazz, status.ToString().c_str());
}

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }

    register_com_litl_leveldb_DB(env);
    register_com_litl_leveldb_WriteBatch(env);
    register_com_litl_leveldb_Iterator(env);

    return JNI_VERSION_1_6;
}
