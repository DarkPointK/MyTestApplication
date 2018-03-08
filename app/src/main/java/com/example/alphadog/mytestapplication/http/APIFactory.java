package com.example.alphadog.mytestapplication.http;

import com.example.alphadog.mytestapplication.http.bean.Result;
import com.example.alphadog.mytestapplication.http.bean.ResultLogin;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Alpha Dog on 2017/12/12.
 */

public class APIFactory {
    private Retrofit mRetrofit;
    private APIInterface mAPIInterface;

    public static final String HTTP = "http://jaq.ncgl.cn/jardms/";

    public static APIFactory getInstance() {
        return FactoryHolder.INSTANCE;
    }

    private static class FactoryHolder {
        private static final APIFactory INSTANCE = new APIFactory();
    }

    private APIFactory() {
        mRetrofit = new Retrofit.Builder()
                        .baseUrl("")
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        mAPIInterface= mRetrofit.create(APIInterface.class);
    }

    public void login(String username, String password, Subscriber<ResultLogin> subscriber){
        Observable observable=mAPIInterface.login(username,password).map(new HttpResultFunc<ResultLogin>());
    }

    public class HttpResultFunc<T> implements Func1<Result<T>, T> {

        @Override
        public T call(Result<T> httpResult) {
            return httpResult.getResponse();
        }
    }
}
