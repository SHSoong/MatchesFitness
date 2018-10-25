package com.match.app.ui.info;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherPersonInfoActivity extends BaseActivity {
    public static final String INFO_KEY = "info";
    @BindView(R.id.img_bg_colum)
    ImageView imgBgColum;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_age_position)
    TextView tvAgePosition;
    @BindView(R.id.tv_credit_score)
    TextView tvCreditScore;
    @BindView(R.id.ll_add)
    LinearLayout llAdd;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.ll_record)
    LinearLayout llRecord;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.gv_record)
    GridView gvRecord;
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    private Person person;
    private List<Person> records;
    private RecordListAdapter adapter;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_other_info);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        person = extras.getParcelable(INFO_KEY);
        if (person != null) {
            if (person.isFriend()) {
                llAdd.setVisibility(View.GONE);
                llRecord.setVisibility(View.VISIBLE);
                llShare.setVisibility(View.VISIBLE);
            } else {
                llAdd.setVisibility(View.VISIBLE);
                llRecord.setVisibility(View.GONE);
            }
        }
        Glide.with(mContext)
                .load(person.getLogUrl())
                .placeholder(R.mipmap.anim_avitor)
                .error(R.mipmap.anim_avitor)
                .dontAnimate()
                .into(imgBgColum);

        tvAgePosition.setText("24.广州");
        tvCreditScore.setText("信用值：72分");
        tvName.setText(person.getName());
        getData();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShareDialog.Builder(mContext)
                        .setCancelable(false)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (view.getId()) {

                                }
                            }
                        }).create();
            }
        });
    }

    /****
     * 获取数据
     */
    private void getData() {
        tvRecord.setText(person.getName() + "的配对记录");
        records = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            records.add(new Person("测试" + i, "", true));
        }
        gvRecord.setNumColumns(4);
        adapter = new RecordListAdapter(mContext, records, true);
        gvRecord.setAdapter(adapter);

        setListViewHeightBasedOnChildren(gvRecord);
        adapter.notifyDataSetChanged();
    }

    public void setListViewHeightBasedOnChildren(GridView view) {
        // 获取listview的adapter
        ListAdapter listAdapter = view.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, view);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = view.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        view.setLayoutParams(params);
    }
}
