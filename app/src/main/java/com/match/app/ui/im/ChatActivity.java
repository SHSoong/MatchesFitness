package com.match.app.ui.im;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.match.app.message.entity.User;
import com.match.app.db.MessageDao;
import com.match.app.message.entity.Conversation;
import com.match.app.message.entity.Message;
import com.match.app.receiver.NewsBroadCastReceiver;
import com.match.app.ui.adapter.ChatAdapter;
import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*******
 * 会话界面
 */
public class ChatActivity extends BaseActivity implements NewsBroadCastReceiver.MessageListener {
    public static final String DATA = "data";
    @BindView(R.id.rcv_conversation)
    RecyclerView rcvConversation;
    @BindView(R.id.lst_message)
    ListView lstMessage;
    private Conversation conversation;
    private List<Message> messageList;
    private ChatAdapter adapter;
    private List<String> stringList;
    private MessageDao dao;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_chat);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        NewsBroadCastReceiver.register(this);
        dao = new MessageDao(this);
        conversation = getIntent().getParcelableExtra(DATA);
        if (conversation != null) {
            initTile(conversation.getHisName(), true);
        }
        initData();
        lstMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String msg = stringList.get(i);
                Message message = new Message(conversation.getConversationId(), User.getInstance().getToken(), User.getInstance().getName(), conversation.getConversationId(), conversation.getHisName(),
                        msg, (int) (System.currentTimeMillis() / 1000), conversation.getHisLogoUrl(), 0);
                dao.add(message);
                messageList.clear();
                messageList = dao.queryMessageByConversation(conversation.getConversationId());
                adapter.setData(messageList);
                rcvConversation.scrollToPosition(messageList.size() - 1);
            }
        });
    }

    private void initData() {
        messageList = dao.queryMessageByConversation(conversation.getConversationId());
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

    @Override
    public void notice() {
        messageList.clear();
        messageList = dao.queryMessageByConversation(conversation.getConversationId());
        adapter.setData(messageList);
    }
}
