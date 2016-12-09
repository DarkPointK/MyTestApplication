// IFruitAidlInterface.aidl
package com.example.alphadog.mytestapplication;

// Declare any non-default types here with import statements
import com.example.alphadog.mytestapplication.Fruit;

 interface IFruitAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String getSomeThing();
    Fruit getFruit();
}
