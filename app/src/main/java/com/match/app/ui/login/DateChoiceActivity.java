package com.match.app.ui.login;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.match.app.ui.adapter.DateAdapter;
import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*******
 * 日期选择
 * crate by john
 */
public class DateChoiceActivity extends BaseActivity {
    @BindView(R.id.rcy_month)
    RecyclerView rcyMonth;
    private DateAdapter adapter;
    private List<String> dates;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_date_choice);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        initTile("日期选择", true);
        initData();
    }

    private void initData() {
        dates = new ArrayList<>();
        dates.add("2018/09");
        dates.add("2018/10");
        rcyMonth.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DateAdapter(mContext, dates);
        rcyMonth.setAdapter(adapter);
        int i = adapter.getItemCount();
        Log.i("",i+"");
    }
}
