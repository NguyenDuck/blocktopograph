LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE            := leveldbjni

LOCAL_C_INCLUDES        := $(LOCAL_PATH)/leveldb-mcpe\
                            $(LOCAL_PATH)/leveldb-mcpe/include\
                            $(LOCAL_PATH)/leveldb-mcpe/include/leveldb

LOCAL_CPP_EXTENSION     := .cc

LOCAL_CFLAGS            := -DLEVELDB_PLATFORM_ANDROID -std=gnu++11 -DDLLX=

#Asan
#LOCAL_CFLAGS            += -fsanitize=leak -fno-omit-frame-pointer
#LOCAL_LDFLAGS           := -fsanitize=leak

LOCAL_SRC_FILES         := com_litl_leveldb_DB.cc\
                            com_litl_leveldb_Iterator.cc\
                            com_litl_leveldb_WriteBatch.cc\
                            leveldbjni.cc\

LOCAL_STATIC_LIBRARIES  +=  leveldb-mcpe

LOCAL_LDLIBS            +=  -llog -ldl -lz

LOCAL_CPP_FEATURES      += exceptions

#Asan
#LD_PRELOAD              += libclang_rt.asan-i686-android.so

include $(BUILD_SHARED_LIBRARY)

####################################################################################################

include $(CLEAR_VARS)

LOCAL_MODULE            := leveldb-mcpe

LOCAL_CFLAGS            := -O3 -D_REENTRANT -DOS_ANDROID -DLEVELDB_PLATFORM_POSIX\
                            -DNDEBUG -std=gnu++11 -DDLLX=

LOCAL_CPP_EXTENSION     := .cc

LOCAL_C_INCLUDES        := $(LOCAL_PATH)/leveldb-mcpe/include\
                            $(LOCAL_PATH)/leveldb-mcpe/include/leveldb\
                            $(LOCAL_PATH)/leveldb-mcpe

LOCAL_SRC_FILES         := \
        leveldb-mcpe/db/builder.cc                  leveldb-mcpe/db/c.cc\
        leveldb-mcpe/db/dbformat.cc                 leveldb-mcpe/db/db_impl.cc\
        leveldb-mcpe/db/db_iter.cc                  leveldb-mcpe/db/dumpfile.cc\
        leveldb-mcpe/db/filename.cc                 leveldb-mcpe/db/log_reader.cc\
        leveldb-mcpe/db/log_writer.cc               leveldb-mcpe/db/memtable.cc\
        leveldb-mcpe/db/repair.cc                   leveldb-mcpe/db/table_cache.cc\
        leveldb-mcpe/db/version_edit.cc             leveldb-mcpe/db/version_set.cc\
        leveldb-mcpe/db/write_batch.cc              leveldb-mcpe/db/zlib_compressor.cc\
        leveldb-mcpe/table/block_builder.cc         leveldb-mcpe/table/block.cc\
        leveldb-mcpe/table/filter_block.cc          leveldb-mcpe/table/format.cc\
        leveldb-mcpe/table/iterator.cc              leveldb-mcpe/table/merger.cc\
        leveldb-mcpe/table/table_builder.cc         leveldb-mcpe/table/table.cc\
        leveldb-mcpe/table/two_level_iterator.cc    leveldb-mcpe/util/arena.cc\
        leveldb-mcpe/util/bloom.cc                  leveldb-mcpe/util/cache.cc\
        leveldb-mcpe/util/coding.cc                 leveldb-mcpe/util/comparator.cc\
        leveldb-mcpe/util/crc32c.cc                 leveldb-mcpe/util/env_boost.cc\
        leveldb-mcpe/util/env.cc                    leveldb-mcpe/util/env_posix.cc\
        leveldb-mcpe/util/env_win.cc                leveldb-mcpe/util/filter_policy.cc\
        leveldb-mcpe/util/hash.cc                   leveldb-mcpe/util/histogram.cc\
        leveldb-mcpe/util/logging.cc                leveldb-mcpe/util/options.cc\
        leveldb-mcpe/util/status.cc                 leveldb-mcpe/util/win_logger.cc\
        leveldb-mcpe/port/port_posix.cc             leveldb-mcpe/port/port_posix_sse.cc

include $(BUILD_STATIC_LIBRARY)
