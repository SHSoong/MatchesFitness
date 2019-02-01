package com.match.app.ui.info;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.match.app.base.BaseActivity;
import com.match.app.common.User;
import com.match.app.customer.ArcImageView;
import com.match.app.message.entity.Person;
import com.match.app.ui.adapter.RecordListAdapter;
import com.match.app.utils.DateUtils;
import com.matches.fitness.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherPersonInfoActivity extends BaseActivity {
    public static final String INFO_KEY = "info";

    @BindView(R.id.rlLeftBack)
    RelativeLayout rlLeftBack;

    @BindView(R.id.arcImageView)
    ArcImageView arcImageView;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.ivEdit)
    TextView ivEdit;
    @BindView(R.id.tvAge)
    TextView tvAge;

    @BindView(R.id.llAdd)
    LinearLayout llAdd;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.llRecord)
    LinearLayout llRecord;
    @BindView(R.id.tvRecord)
    TextView tvRecord;

    @BindView(R.id.gvRecord)
    GridView gvRecord;

    private List<Person> records;
    private RecordListAdapter adapter;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_other_info);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);

        rlLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvName.setText(User.getInstance().getName());
        tvAge.setText(String.valueOf(DateUtils.getAge(DateUtils.parse(User.getInstance().getBirthday()))));
        ivEdit.setVisibility(View.GONE);

//        Bundle extras = getIntent().getExtras();
//        person = extras.getParcelable(INFO_KEY);
//        if (person != null) {
//            if (person.isFriend()) {
//                llAdd.setVisibility(View.GONE);
//                llRecord.setVisibility(View.VISIBLE);
//            } else {
//                llAdd.setVisibility(View.VISIBLE);
//                llRecord.setVisibility(View.GONE);
//            }
//        }

        Glide.with(mContext)
                .load("")
                .apply(new RequestOptions().placeholder(R.mipmap.avatar_bg_icon))
                .into(arcImageView);
        tvRecord.setText("配对记录");
        getData();
    }

    /****
     * 获取数据
     */
    private void getData() {
        records = new ArrayList<>();
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
