//
// Created by xiongda on 2017/8/9.
//


#include <jni.h>
//#include "mymath.cpp"

JNIEXPORT jint JNICALL
Java_com_xiongda_ndklearning_MainActivity_getResult(jint x, jint y) {
    return x + y;
}

