package com.match.app.ui.im;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

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

        initTitleBar();
        dao = new BaseDao(Conversation.class);

        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initTitleBar() {
        initTile("社交", true);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ContactsListActivity.class));
            }
        });
    }

    private void initData() {
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
                Conversation conversation = lists.get(position);
                Intent intent = new Intent(mContext, ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(ChatActivity.DATA, conversation);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        getDataList();
    }

    private void getDataList() {
        lists = dao.queryAll();
        mAdapter.addData(lists);
    }

}
