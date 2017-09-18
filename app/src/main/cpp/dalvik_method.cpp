//
// Created by sunxiaoyu on 2017/9/14.
//
#include <string>
#include "dalvik_method.h"

typedef Object *(*FindObject)(void *thread, jobject jobject1);
typedef  void* (*FindThread)();
FindObject  findObject;
FindThread  findThread;

extern "C"
JNIEXPORT void JNICALL
Java_com_sxy_andfixdemo_FixManager_replaceDalvik(JNIEnv *env, jobject instance, jint sdk, jobject wrongMethod, jobject rightMethod) {


    Method *wrong = (Method *) env->FromReflectedMethod(wrongMethod);

    Method *right =(Method *) env->FromReflectedMethod(rightMethod);

    //把right 对应Object   第一个成员变量ClassObject   status
    //ClassObject
    void *dvm_hand=dlopen("libdvm.so", RTLD_NOW);
    // sdk  10    以前是这样   10会发生变化
    findObject= (FindObject) dlsym(dvm_hand, sdk > 10 ?
                                             "_Z20dvmDecodeIndirectRefP6ThreadP8_jobject" :
                                             "dvmDecodeIndirectRef");
    findThread = (FindThread) dlsym(dvm_hand, sdk > 10 ? "_Z13dvmThreadSelfv" : "dvmThreadSelf");
    // method   所声明的Class
    jclass methodClaz = env->FindClass("java/lang/reflect/Method");
    jmethodID rightMethodId = env->GetMethodID(methodClaz, "getDeclaringClass",
                                               "()Ljava/lang/Class;");

    jobject ndkObject = env->CallObjectMethod(rightMethod, rightMethodId);
    ClassObject *firstFiled = (ClassObject *) findObject(findThread(), ndkObject);
    firstFiled->status=CLASS_INITIALIZED;
    wrong->accessFlags |= ACC_PUBLIC;
    wrong->methodIndex=right->methodIndex;
    wrong->jniArgInfo=right->jniArgInfo;
    wrong->registersSize=right->registersSize;
    wrong->outsSize=right->outsSize;
    //方法参数 原型
    wrong->prototype=right->prototype;
    wrong->insns=right->insns;
    wrong->nativeFunc=right->nativeFunc;

}


