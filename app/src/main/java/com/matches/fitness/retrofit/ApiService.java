package com.matches.fitness.retrofit;

import com.matches.fitness.datamodel.LoginModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginModel> doLogin(@Field("userName") String userName, @Field("userPwd") String userPwd);

}
