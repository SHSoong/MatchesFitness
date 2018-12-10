package com.match.app.retrofit.manager;

import android.util.Log;

import com.match.app.message.bean.BaseResponse;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T> {

    private static final String TAG = "BaseObserver";

    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "onSubscribe");
    }

    @Override
    public void onNext(T t) {
        if (((BaseResponse) t).isSuccess()) {
            onHandleSuccess(t);
        } else {
            onHandleError(((BaseResponse) t).getMessage());
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "error:" + e.toString());
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
    }


    protected abstract void onHandleSuccess(T t);

    protected abstract void onHandleError(String msg);
}
