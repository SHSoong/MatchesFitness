package com.match.app.ui.im;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;


import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConversationListActivity extends BaseActivity {

    private List<Conversation> lists;
    @BindView(R.id.lst_conversation)
    ListView lstConversation;
    @BindView(R.id.img_right)
    ImageView imgRight;
    private ConversationListAdapter listAdapter;

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
    }

    private void initData() {
        lists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            // int id, String him, String hisName, String hisLogoUrl
            lists.add(new Conversation(i, "00000" + i, "好友" + i, ""));
        }
        listAdapter = new ConversationListAdapter(mContext, lists);
        lstConversation.setAdapter(listAdapter);
        lstConversation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Conversation conversation = lists.get(i - 1);
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
                                lists.remove(position - 1);
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
}
