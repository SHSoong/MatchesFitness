package com.matches.fitness.ui.im;

import com.matches.fitness.R;
import com.matches.fitness.base.BaseActivity;

/*******
 * 会话界面
 */
public class ChatActivity extends BaseActivity {
    public static final String DATA = "data";
    private Conversation conversation;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_chat);
    }

    @Override
    protected void onInit() {
        conversation = getIntent().getParcelableExtra(DATA);
        if (conversation != null) {
            initTile(conversation.getHisName(), true);
        }


    }
}
