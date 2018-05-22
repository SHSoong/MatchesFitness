package com.matches.fitness.base;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@AutoValue
public abstract class BaseResponse<T> implements Serializable {

    @SerializedName("code")
    public abstract int code();

    @SerializedName("msg")
    public abstract String msg();

    @SerializedName("result")
    public abstract T result();

}
