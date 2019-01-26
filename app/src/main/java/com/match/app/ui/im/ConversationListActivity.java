package com.match.app.ui.im;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;


import com.match.app.db.ConversationDao;
import com.match.app.message.entity.Conversation;
import com.match.app.receiver.NewsBroadCastReceiver;
import com.match.app.ui.adapter.ConversationListAdapter;
import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConversationListActivity extends BaseActivity implements NewsBroadCastReceiver.MessageListener {

    private List<Conversation> lists;
    @BindView(R.id.lst_conversation)
    ListView lstConversation;
    @BindView(R.id.img_right)
    ImageView imgRight;
    private ConversationListAdapter listAdapter;

    private ConversationDao dao;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_conversation_list);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        initData();
        initTile("社交", true);
        imgRight.setImageResource(R.mipmap.icon_search);
        NewsBroadCastReceiver.register(this);
    }

    private void initData() {
        dao = new ConversationDao(mContext);
        lists = dao.queryAll();

        listAdapter = new ConversationListAdapter(mContext, lists);
        lstConversation.setAdapter(listAdapter);
        lstConversation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Conversation conversation = lists.get(i);
                Intent intent = new Intent(mContext, ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(ChatActivity.DATA, conversation);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        lstConversation.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
                new AlertDialog.Builder(mContext)
                        .setMessage("是否删除该对话")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Conversation conversation = lists.get(i);
                                dao.delete(conversation);
                                lists.remove(conversation);
                                listAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("取消", null).create().show();
                return true;
            }
        });
        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ContactsListActivity.class));
            }
        });
    }

    @Override
    public void notice() {
        lists = dao.queryAll();
        listAdapter.setData(lists);
    }
}
