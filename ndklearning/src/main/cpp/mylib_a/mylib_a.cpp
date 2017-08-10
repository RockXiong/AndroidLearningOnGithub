//
// Created by xiongda on 2017/8/9.
//

#include <jni.h>
#include "mymath.h"

extern "C" {
JNIEXPORT jint JNICALL
Java_com_xiongda_ndklearning_MainActivity_add(JNIEnv *env, jobject thiz, int x, int y) {
    return MyMath::add(x, y);
}

}

