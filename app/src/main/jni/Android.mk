LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := hello_jni

LOCAL_SRC_FILES := com_example_alphadog_mytestapplication_jni_HelloJni.c

include $(BUILD_SHARED_LIBRARY)