package com.matches.fitness.base;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseEntity<T> implements Serializable {

    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private String msg;

    @SerializedName("result")
    private T result;

    public boolean isSuccess() {
        return code == 0;
    }

    public T getResult(){
        return result;
    }

    public String getMsg(){
        return msg;
    }

}
