package com.matches.fitness.retrofit;

import com.matches.fitness.base.BaseEntity;
import com.matches.fitness.entity.LoginEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseEntity<LoginEntity>> doLogin(@Field("userName") String userName, @Field("userPwd") String userPwd);

}
