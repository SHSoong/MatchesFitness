package com.match.app.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.matches.fitness.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {

    protected final static String PHONE = "phone";

    public CompositeDisposable disposables = new CompositeDisposable();

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        onInitBinding();
        onInit();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setStatusBarColor(R.color.graySolBg);
    }

    /*******
     * 初始化标题栏
     * @param resid
     *         标题
     * @param isBack
     *         是否显示后退键
     */
    protected void initTile(int resid, boolean isBack) {
        TextView textView = findViewById(R.id.tvTitle);
        textView.setText(resid);
        RelativeLayout rlLeftBack = findViewById(R.id.rlLeftBack);
        if (!isBack) {
            rlLeftBack.setVisibility(View.GONE);
            return;
        }
        rlLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /*******
     * 初始化标题栏
     * @param resid
     *         标题
     * @param isBack
     *         是否显示后退键
     */
    protected void initTile(String resid, boolean isBack) {
        TextView textView = findViewById(R.id.tvTitle);
        textView.setText(resid);
        RelativeLayout rlLeftBack = findViewById(R.id.rlLeftBack);
        if (!isBack) {
            rlLeftBack.setVisibility(View.GONE);
            return;
        }
        rlLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

    public void setStatusBarColor(int colorPrimary) {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarColor(colorPrimary)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .navigationBarColor(colorPrimary)
                .init();
    }
}
