package com.matches.fitness.retrofit;

import com.match.app.message.bean.B001Response;
import com.matches.fitness.base.BaseEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("match-app/android/")
    Observable<BaseEntity<B001Response>> doLogin(@Field("actionCode") String actionCode, @Field("loginName") String loginName,
                                                 @Field("password") String password, @Field("deviceType") String deviceType);

}
