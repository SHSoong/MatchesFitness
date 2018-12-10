package com.match.app.retrofit;

import com.match.app.message.bean.B001Request;
import com.match.app.message.bean.B001Response;
import com.match.app.message.bean.B002Request;
import com.match.app.message.bean.B002Response;
import com.match.app.message.bean.B003Request;
import com.match.app.message.bean.B003Response;
import com.match.app.message.bean.B301Request;
import com.match.app.message.bean.B301Response;
import com.match.app.message.bean.B332Request;
import com.match.app.message.bean.B332Response;

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
    Observable<B301Response> getFitnessList(@Body B301Request request);

    @POST("android/")
    Observable<B332Response> getSearchResultList(@Body B332Request request);

}
