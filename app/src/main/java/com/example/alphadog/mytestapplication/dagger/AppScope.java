package com.example.alphadog.mytestapplication.dagger;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Alpha Dog on 2016/11/30.
 */
@Scope
@Retention(RUNTIME)
public @interface AppScope {
}