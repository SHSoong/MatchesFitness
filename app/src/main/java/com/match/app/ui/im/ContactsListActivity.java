package com.match.app.ui.im;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.match.app.base.BaseActivity;
import com.match.app.customer.WordsNavigation;
import com.match.app.message.entity.Contact;
import com.match.app.message.table.Conversation;
import com.match.app.ui.adapter.ContactListAdapter;
import com.matches.fitness.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*******
 * create by john
 * 联系人列表
 */
public class ContactsListActivity extends BaseActivity implements WordsNavigation.OnWrodsChangeListener {

    @BindView(R.id.tvLeft)
    TextView tvLeft;
    @BindView(R.id.ivRight)
    ImageView ivRight;
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.tv_position)
    TextView tvPosition;

    private List<Contact> lists;
    private ContactListAdapter adapter;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_contact_list);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);

        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivRight.setVisibility(View.INVISIBLE);

        initData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact contact = lists.get(i);
                Conversation conversation = new Conversation();
                Intent intent = new Intent(mContext, ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(ChatActivity.DATA, conversation);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        lists = new ArrayList<>();
        Collections.sort(lists, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact, Contact t1) {
                return contact.getPinyin().compareTo(t1.getPinyin());
            }
        });
        adapter = new ContactListAdapter(mContext, lists);
        listView.setAdapter(adapter);
    }

    @Override
    public void wordsChange(String words) {
        updateWord(words);
        updateListView(words);
    }

    /**
     * @param words 首字母
     */
    private void updateListView(String words) {
        for (int i = 0; i < lists.size(); i++) {
            String headerWord = lists.get(i).getHeaderWord();
            //将手指按下的字母与列表中相同字母开头的项找出来
            if (words.equals(headerWord)) {
                //将列表选中哪一个
                listView.setSelection(i);
                //找到开头的一个即可
                return;
            }
        }
    }

    public void updateWord(String words) {
        tvPosition.setText(words);
        tvPosition.setVisibility(View.VISIBLE);
        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessageDelayed(0, 500);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    tvPosition.setVisibility(View.GONE);
                    break;
            }
        }
    };
}

