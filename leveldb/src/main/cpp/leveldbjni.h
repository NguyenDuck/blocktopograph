#ifndef LEVELDBJNI_H_
#define LEVELDBJNI_H_

#include <jni.h>
#include <android/log.h>
#include "leveldb/status.h"

# define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))
#define  LOG_TAG    "LevelDB"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

jint throwException(JNIEnv* env, leveldb::Status status);

#endif /* LEVELDBJNI_H_ */
