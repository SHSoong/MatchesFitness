package com.match.app.retrofit;

import com.match.app.message.bean.*;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("android/")
    Observable<B001Response> doLogin(@Body B001Request request);

    @POST("android/")
    Observable<B002Response> doRegister(@Body B002Request request);

    @POST("android/")
    Observable<B003Response> doRestPassword(@Body B003Request request);

    @POST("android/")
    Observable<B005Response> doB005Request(@Body B005Request request);

    @POST("android/")
    Observable<BaseResponse> doB200Request(@Body B200Request request);

    @POST("android/")
    Observable<B301Response> doB301Request(@Body B301Request request);

    @POST("android/")
    Observable<BaseResponse> doB330Request(@Body B330Request request);

    @POST("android/")
    Observable<B334Response> doB334Request(@Body B334Request request);

    @POST("android/")
    Observable<B332Response> getSearchResultList(@Body B332Request request);

    @POST("android/")
    Observable<B336Response> getPairList(@Body B336Request request);

    @POST("android/")
    Observable<BaseResponse> doB335Request(@Body B335Request request);

    @POST("android/")
    Observable<BaseResponse> doAgreeOrRefusePair(@Body B337Request request);

}
