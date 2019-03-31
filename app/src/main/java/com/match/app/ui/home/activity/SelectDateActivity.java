package com.match.app.ui.home.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.match.app.base.BaseActivity;
import com.match.app.config.AppConstant;
import com.match.app.message.entity.TimeInterval;
import com.match.app.utils.LocalJsonUtils;
import com.matches.fitness.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectDateActivity extends BaseActivity implements CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener {

    TextView mTextMonthDay;
    TextView mTextYear;
    TextView mTextLunar;
    TextView mTextCurrentDay;
    CalendarView mCalendarView;
    RelativeLayout mRelativeTool;
    private int mYear;
    CalendarLayout mCalendarLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    BaseQuickAdapter mAdapter;

    private int sYear;
    private int sMonth;
    private int sDay;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_selectdate);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        setStatusBarDarkMode();
        mTextMonthDay = (TextView) findViewById(R.id.tv_month_day);
        mTextYear = (TextView) findViewById(R.id.tv_year);
        mTextLunar = (TextView) findViewById(R.id.tv_lunar);
        mRelativeTool = (RelativeLayout) findViewById(R.id.rl_tool);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mTextCurrentDay = (TextView) findViewById(R.id.tv_current_day);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });

        mCalendarLayout = (CalendarLayout) findViewById(R.id.calendarLayout);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
        sYear = mCalendarView.getCurYear();
        sMonth = mCalendarView.getCurMonth();
        sDay = mCalendarView.getCurDay();
        initData();
    }

    protected void initData() {

//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        recyclerView.addItemDecoration(new RecyclerItemDecoration(15));
        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<TimeInterval.Interval, BaseViewHolder>(R.layout.item_list_article) {
            @Override
            protected void convert(BaseViewHolder helper, TimeInterval.Interval item) {
                helper.setText(R.id.tv_content, item.getTimeStart() + " - " + item.getTimeEnd());
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TimeInterval.Interval bean = (TimeInterval.Interval) mAdapter.getItem(position);
                if (bean == null) return;
                Intent it = new Intent();
                it.putExtra(AppConstant.INTENT_INTERVAL_TIME_START, bean.getTimeStart());
                it.putExtra(AppConstant.INTENT_INTERVAL_TIME_END, bean.getTimeEnd());
                it.putExtra(AppConstant.INTENT_INTERVAL_DATE_YEAR, sYear);
                it.putExtra(AppConstant.INTENT_INTERVAL_DATE_MONTH, sMonth);
                it.putExtra(AppConstant.INTENT_INTERVAL_DATE_DAY, sDay);
                setResult(RESULT_OK, it);
                finish();
            }
        });

        String jsonStr = new LocalJsonUtils().getJson(this, "timeJson");
        List<TimeInterval.Interval> list = new Gson().fromJson(jsonStr, TimeInterval.class).getResult();
        mAdapter.addData(list);
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();

        sYear = calendar.getYear();
        sMonth = calendar.getMonth();
        sDay = calendar.getDay();
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }


}
