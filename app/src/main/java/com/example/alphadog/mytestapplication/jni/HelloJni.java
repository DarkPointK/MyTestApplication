package com.example.alphadog.mytestapplication.jni;

/**
 * Created by Alpha Dog on 2016/12/10.
 */

public class HelloJni {
    static {
        System.loadLibrary("hello_jni");
    }
    public static native String helloJni();
    public static native int addJni(int a,int b);
}
