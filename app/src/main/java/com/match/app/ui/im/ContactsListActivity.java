package com.match.app.ui.im;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

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
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.words_view)
    WordsNavigation wordsView;
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
        wordsView.setListener(this);
        initData();
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                wordsView.setTouchIndex(lists.get(i).getHeaderWord());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact contact = lists.get(i);
                Conversation conversation = new Conversation(i,"",contact.getName(),contact.getLogUrl());
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
        lists.add(new Contact("DAVE", null));
        lists.add(new Contact("阿忠", null));
        lists.add(new Contact("老宋", null));
        lists.add(new Contact("旺仔", null));
        lists.add(new Contact("凯歌", null));
        lists.add(new Contact("刘乐", null));
        lists.add(new Contact("严峻", null));
        lists.add(new Contact("三爷", null));
        lists.add(new Contact("小勇", null));
        lists.add(new Contact("伟哥", null));
        lists.add(new Contact("唐伟", null));
        lists.add(new Contact("定时兄", null));
        lists.add(new Contact("john", null));
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

