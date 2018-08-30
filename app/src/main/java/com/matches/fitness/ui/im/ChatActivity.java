package com.matches.fitness.ui.im;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.matches.fitness.R;
import com.matches.fitness.base.BaseActivity;
import com.matches.fitness.common.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*******
 * 会话界面
 */
public class ChatActivity extends BaseActivity {
    public static final String DATA = "data";
    @BindView(R.id.rcv_conversation)
    RecyclerView rcvConversation;
    @BindView(R.id.lst_message)
    ListView lstMessage;
    private Conversation conversation;
    private List<Message> messageList;
    private ChatAdapter adapter;
    private List<String> stringList;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_chat);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        conversation = getIntent().getParcelableExtra(DATA);
        if (conversation != null) {
            initTile(conversation.getHisName(), true);
        }
        initData();
        lstMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String message = stringList.get(i);
                // int id, int conversation, String speaker, String speakerName, String content, int time, int status
                messageList.add(new Message(0, conversation.getId(), conversation.getHim(),
                        conversation.getHisName(), message, (int) System.currentTimeMillis() / 1000, 0));
                adapter.notifyDataSetChanged();
                rcvConversation.scrollToPosition(messageList.size() - 1);
            }
        });
    }

    private void initData() {
        messageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                messageList.add(new Message(i, conversation.getId(), conversation.getHim(),
                        conversation.getHisName(), "你好" + i, (int) System.currentTimeMillis() / 1000, 0));
            } else {
                messageList.add(new Message(i, conversation.getId(), "",
                        "我", "你好" + i, (int) System.currentTimeMillis() / 1000, 0));
            }
        }
        rcvConversation.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(messageList, mContext);
        rcvConversation.setAdapter(adapter);
        rcvConversation.scrollToPosition(messageList.size() - 1);

        stringList = new ArrayList<>();
        stringList.add("你好，你到达指定健身房了吗？");
        stringList.add("你好，我已经到达指定健身房了");
        stringList.add("到了");
        stringList.add("约到你好开心");
        ArrayAdapter<String> adapter = new ArrayAdapter(mContext, R.layout.itemview_message, R.id.tv_message, stringList);
        lstMessage.setAdapter(adapter);
    }
}
