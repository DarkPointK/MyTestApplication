/* DO NOT EDIT THIS FILE - it is machine generated */
#include <com_example_alphadog_mytestapplication_jni_HelloJni.h>
/* Header for class com_example_alphadog_mytestapplication_jni_HelloJni */


/*
 * Class:     com_example_alphadog_mytestapplication_jni_HelloJni
 * Method:    helloJni
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_example_alphadog_mytestapplication_jni_HelloJni_helloJni
  (JNIEnv *env, jclass jobj){
    return (*env)->NewStringUTF(env,"Hello Jni");
}

/*
 * Class:     com_example_alphadog_mytestapplication_jni_HelloJni
 * Method:    addJni
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_example_alphadog_mytestapplication_jni_HelloJni_addJni
  (JNIEnv *env, jclass jobj, jint ja, jint jb){
    return ja+jb;
}