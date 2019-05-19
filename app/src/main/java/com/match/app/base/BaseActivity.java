package com.match.app.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.match.app.manager.LoadingManager;
import com.matches.fitness.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private static boolean isMiUi = false;

    public CompositeDisposable disposables = new CompositeDisposable();

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        onInitBinding();
        onInit();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        setStatusBarColor(R.color.graySolBg);
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
     * @param listener
     *
     */
    protected void initTile(int resid, final View.OnClickListener listener) {
        TextView textView = findViewById(R.id.tvTitle);
        textView.setText(resid);
        RelativeLayout rlLeftBack = findViewById(R.id.rlLeftBack);
        if (listener == null) {
            rlLeftBack.setVisibility(View.GONE);
            return;
        }
        rlLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
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

    public void showLoading() {
        LoadingManager.show(this);
    }

    public void canselLoading() {
        LoadingManager.cansel();
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 设置小米黑色状态栏字体
     */
    @SuppressLint("PrivateApi")
    private void setMIUIStatusBarDarkMode() {
        if (isMiUi) {
            Class<? extends Window> clazz = getWindow().getClass();
            try {
                int darkModeFlag;
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                extraFlagField.invoke(getWindow(), darkModeFlag, darkModeFlag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 静态域，获取系统版本是否基于MIUI
     */

    static {
        try {
            @SuppressLint("PrivateApi") Class<?> sysClass = Class.forName("android.os.SystemProperties");
            Method getStringMethod = sysClass.getDeclaredMethod("get", String.class);
            String version = (String) getStringMethod.invoke(sysClass, "ro.miui.ui.version.name");
            isMiUi = version.compareTo("V6") >= 0 && Build.VERSION.SDK_INT < 24;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置魅族手机状态栏图标颜色风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean setMeiZuDarkMode(Window window, boolean dark) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 24) {
            return false;
        }
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @SuppressLint("InlinedApi")
    private int getStatusBarLightMode() {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isMiUi) {
                result = 1;
            } else if (setMeiZuDarkMode(getWindow(), true)) {
                result = 2;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result = 3;
            }
        }
        return result;
    }


    @SuppressLint("InlinedApi")
    protected void setStatusBarDarkMode() {
        int type = getStatusBarLightMode();
        if (type == 1) {
            setMIUIStatusBarDarkMode();
        } else if (type == 2) {
            setMeiZuDarkMode(getWindow(), true);
        } else if (type == 3) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
