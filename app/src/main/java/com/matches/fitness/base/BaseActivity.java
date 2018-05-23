package com.matches.fitness.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.matches.fitness.utils.StatusBarUtil;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {

    public CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInitBinding();
        onInit();
        setTranslucentStatus();
        setTranslucentStatusPadding();
        setStausBarTextDeep(true);
    }

    @Override
    protected void onDestroy() {
        if (!disposables.isDisposed()) {
            disposables.clear();
        }
        super.onDestroy();
    }

    /**
     * 设置状态栏文字高亮显示
     *
     * @param isDark
     */
    public void setStausBarTextDeep(boolean isDark) {
        if (isDark == true) {
            StatusBarUtil.setStatusBarTextColor(this, true);
        } else {
            StatusBarUtil.setStatusBarTextColor(this, false);
        }

    }
    /**
     * 设置状态栏颜色
     *
     * @param color
     */

    public void setStatusColor(int color) {
        StatusBarUtil.setColor(this, color);
    }

    /**
     * 设置全屏模式透明
     */
    public void setTranslucentStatus() {
        StatusBarUtil.setTranslucentStatus(this, true);
    }

    /**
     * 设置全屏模式透明内容padding
     */
    public void setTranslucentStatusPadding() {
        StatusBarUtil.setTransparent(this);
    }

    protected abstract void onInitBinding();

    protected abstract void onInit();

    public void addSubscription(Disposable disposable) {
        disposables.add(disposable);
    }
}
