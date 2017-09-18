//
// Created by sunxiaoyu on 2017/9/14.
//

#include <jni.h>

#include <string>
#include "art_7_0.h"

extern "C"
JNIEXPORT void JNICALL
Java_com_sxy_andfixdemo_FixManager_replaceArt7_10(JNIEnv *env, jobject instance, jint sdk,
                                                  jobject wrongMethod, jobject rightMethod) {

    //    art虚拟机替换  art  ArtMethod  ---》Java方法
    art::mirror::ArtMethod *wrong = (art::mirror::ArtMethod *) env->FromReflectedMethod(wrongMethod);
    art::mirror::ArtMethod *right = (art::mirror::ArtMethod *) env->FromReflectedMethod(rightMethod);


    wrong->declaring_class_=right->declaring_class_;
    wrong->dex_code_item_offset_=right->dex_code_item_offset_;
    wrong->method_index_=right->method_index_;
    wrong->dex_method_index_=right->dex_method_index_;

    //入口
    wrong->ptr_sized_fields_.entry_point_from_jni_=right->ptr_sized_fields_.entry_point_from_jni_;
    //机器码模式
    wrong->ptr_sized_fields_.entry_point_from_quick_compiled_code_=right->ptr_sized_fields_.entry_point_from_quick_compiled_code_;
    //不一样
    wrong->ptr_sized_fields_.entry_point_from_jni_=right->ptr_sized_fields_.entry_point_from_jni_;
    wrong->ptr_sized_fields_.dex_cache_resolved_methods_=right->ptr_sized_fields_.dex_cache_resolved_methods_;
    wrong->ptr_sized_fields_.dex_cache_resolved_types_=right->ptr_sized_fields_.dex_cache_resolved_types_;
    wrong->hotness_count_=right->hotness_count_;

}

