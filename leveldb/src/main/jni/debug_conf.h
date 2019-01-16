//
// Created by barco on 2018/4/12.
//

#ifndef CONVARTER_DEBUG_CONF_H
#define CONVARTER_DEBUG_CONF_H

//Chunk
//#define LOG_CHUNK_OPERATION
#define LOG_CHUNK_LOADSAVE

//World
//#define LOG_SAVDB_OPERATION
#define LOG_SAVDB_LOADSAVE
#define LOG_SAVDB_LRU

#ifdef LOG_CHUNK_OPERATION
#ifndef LOG_CHUNK_LOADSAVE
#define LOG_CHUNK_LOADSAVE
#endif
#endif

#ifdef LOG_SAVDB_OPERATION
#ifndef LOG_SAVDB_LOADSAVE
#define LOG_SAVDB_LOADSAVE
#endif
#ifndef LOG_SAVDB_LRU
#define LOG_SAVDB_LRU
#endif
#endif

#define CAT(x, y) x y

#endif //CONVARTER_DEBUG_CONF_H
