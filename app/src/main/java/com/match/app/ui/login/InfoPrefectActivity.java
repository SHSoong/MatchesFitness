package com.match.app.ui.login;

import android.view.KeyEvent;

import com.match.app.utils.ToastUtils;
import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by john on 2018/6/27.
 * <p>
 * 注册信息完善界面
 */

public class InfoPrefectActivity extends BaseActivity {
    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_personal_perfect);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        initTile(R.string.info_prefect, false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime;
    /**
     * 退出程序
     */
    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showToast(this, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
