package com.matches.fitness.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;
import com.matches.fitness.R;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {

    public CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInitBinding();
        onInit();
        setStatusBarColor(R.color.white);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    @Override
    protected void onDestroy() {
        if (!disposables.isDisposed()) {
            disposables.clear();
        }
        super.onDestroy();
    }

    public void setStatusBarColor(int colorPrimary){
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarColor(colorPrimary)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .navigationBarColor(colorPrimary)
                .init();
    }

    protected abstract void onInitBinding();

    protected abstract void onInit();

    public void addSubscription(Disposable disposable) {
        disposables.add(disposable);
    }
}
