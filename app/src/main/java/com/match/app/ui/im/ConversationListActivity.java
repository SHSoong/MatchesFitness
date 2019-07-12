package com.match.app.ui.im;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.match.app.base.BaseActivity;
import com.match.app.db.BaseDao;
import com.match.app.message.table.Conversation;
import com.matches.fitness.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConversationListActivity extends BaseActivity {

    @BindView(R.id.rlLeftBack)
    RelativeLayout rlLeftBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivRight)
    ImageView ivRight;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<Conversation> lists = new ArrayList<>();
    private BaseQuickAdapter mAdapter;
    private BaseDao dao;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_conversation_list);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        initTile();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initTile() {
        tvTitle.setText("社交");
        rlLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConversationListActivity.this, ContactsListActivity.class));
            }
        });
    }

    private void initData() {
        dao = new BaseDao(Conversation.class);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<Conversation, BaseViewHolder>(R.layout.itemview_conversation) {
            @Override
            protected void convert(BaseViewHolder helper, Conversation item) {
                helper.setText(R.id.tv_name, item.getHisName());
                helper.setText(R.id.tv_lastmsg, item.getLastMessage());
                helper.setText(R.id.tv_time, item.getLastTime() + "");

                ImageView icon = helper.getView(R.id.img_icon);
                Glide.with(ConversationListActivity.this)
                        .load(item.getHisLogoUrl())
                        .apply(new RequestOptions().placeholder(R.mipmap.icon_avatar))
                        .into(icon);
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Conversation conversation = (Conversation) adapter.getItem(position);
                Intent intent = new Intent(ConversationListActivity.this, ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(ChatActivity.DATA, conversation);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        lists.clear();
        lists = dao.queryAll();
        mAdapter.setNewData(lists);
    }
}
