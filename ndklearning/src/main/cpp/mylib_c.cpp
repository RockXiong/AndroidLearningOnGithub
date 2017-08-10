//
// Created by xiongda on 2017/8/9.
//


#include <jni.h>
#include "mylib_a/mymath.h"

extern "C" {
JNIEXPORT jint JNICALL
Java_com_xiongda_ndklearning_MainActivity_multi2(JNIEnv *env, jobject instance, jint x, jint y) {

    // TODO
    return x * y;
}
JNIEXPORT jint JNICALL
Java_com_xiongda_ndklearning_MainActivity_multi(JNIEnv *env, jobject thiz, jint x, jint y) {
    return x * y;
}

JNIEXPORT jint JNICALL
Java_com_xiongda_ndklearning_MainActivity_callMylibASub(JNIEnv *env,jobject thiz,jint x,jint y){
    return MyMath::sub(x,y);
}

}