package com.matches.fitness.retrofit;

import com.match.app.message.bean.B001Request;
import com.match.app.message.bean.B001Response;
import com.matches.fitness.base.BaseEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("android/")
    Observable<B001Response> doLogin(@Body B001Request request);

}
