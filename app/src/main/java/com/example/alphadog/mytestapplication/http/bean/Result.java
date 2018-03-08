package com.example.alphadog.mytestapplication.http.bean;

/**
 * Created by Alpha Dog on 2017/12/12.
 */

public class Result<T> {

    private String result;
    private T response;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
