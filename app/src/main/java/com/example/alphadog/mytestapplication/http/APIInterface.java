package com.example.alphadog.mytestapplication.http;

import com.example.alphadog.mytestapplication.http.bean.Result;
import com.example.alphadog.mytestapplication.http.bean.ResultLogin;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Alpha Dog on 2017/12/12.
 */

public interface APIInterface {
    @FormUrlEncoded
    @POST("api/login")
    Observable<Result<ResultLogin>> login(@Field("username") String username, @Field("password") String password);
}
