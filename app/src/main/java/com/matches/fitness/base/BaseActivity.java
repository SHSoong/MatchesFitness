package com.matches.fitness.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {

    public CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onInitBinding();
        onInit();
    }

    @Override
    protected void onDestroy() {
        if (!disposables.isDisposed()) {
            disposables.clear();
        }
        super.onDestroy();
    }

    protected abstract void onInitBinding();

    protected abstract void onInit();

    public void addSubscription(Disposable disposable) {
        disposables.add(disposable);
    }
}
