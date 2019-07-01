package com.match.app.ui.im;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.match.app.base.BaseActivity;
import com.match.app.customer.RecordButton;
import com.match.app.customer.StateButton;
import com.match.app.db.BaseDao;
import com.match.app.message.entity.User;
import com.match.app.message.table.Conversation;
import com.match.app.message.table.Message;
import com.match.app.ui.adapter.ChatsAdapter;
import com.match.app.utils.SoftKeyBoardListener;
import com.matches.fitness.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*******
 * 会话界面
 */
public class ChatActivity extends BaseActivity {

    public static final String DATA = "data";

    @BindView(R.id.llContent)
    LinearLayout mLlContent;
    @BindView(R.id.rv_chat_list)
    RecyclerView mRvChat;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.bottom_layout)
    RelativeLayout mRlBottomLayout;//表情,添加底部布局
    @BindView(R.id.ivAdd)
    ImageView mIvAdd;
    @BindView(R.id.btn_send)
    StateButton mBtnSend;//发送按钮

    //
    @BindView(R.id.llTips1)
    LinearLayout llTips1;
    @BindView(R.id.tvTips1)
    TextView tvTips1;
    @BindView(R.id.llTips2)
    LinearLayout llTips2;
    @BindView(R.id.tvTips2)
    TextView tvTips2;
    @BindView(R.id.llTips3)
    LinearLayout llTips3;
    @BindView(R.id.tvTips3)
    TextView tvTips3;
    @BindView(R.id.llTips4)
    LinearLayout llTips4;
    @BindView(R.id.tvTips4)
    TextView tvTips4;

    private MutableLiveData<Boolean> softInputShow = new MutableLiveData<>();
    private Boolean isSoftInputShow = false;
    private Boolean showBottomLayout = false;

    BaseDao dao;
    BaseDao conversationDao;
    Conversation conversation;

    private ChatsAdapter mAdapter;
    public static String mSenderId = "right";
    public static String mTargetId = "left";

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_chat);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);

        conversation = getIntent().getParcelableExtra(DATA);
        mSenderId = User.getInstance().getToken();
        mTargetId = conversation.getHisToken();

        initTile(conversation.getHisName(), true);
        initData(conversation.getHisToken());

        softInputShow.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (showBottomLayout && aBoolean != null && !aBoolean) {
                    showBottomLayout();
                    showBottomLayout = false;
                }
            }
        });
    }

    private void initData(String token) {
        dao = new BaseDao(Message.class);
        conversationDao = new BaseDao(Conversation.class);

        mAdapter = new ChatsAdapter(this, dao.queryByColumn("conversation_token", token));
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(this);
        mLinearLayout.setStackFromEnd(true);
        mRvChat.setLayoutManager(mLinearLayout);
        mRvChat.setAdapter(mAdapter);

        initChatUi();
        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mEtContent.getText().toString().trim().length() > 0) {
                    mBtnSend.setVisibility(View.VISIBLE);
                    mIvAdd.setVisibility(View.GONE);
                } else {
                    mBtnSend.setVisibility(View.GONE);
                    mIvAdd.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                softInputShow.postValue(true);
                isSoftInputShow = true;
            }

            @Override
            public void keyBoardHide(int height) {
                softInputShow.postValue(false);
                isSoftInputShow = false;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initChatUi() {
        //点击空白区域关闭键盘
        mRvChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideBottomLayout();
                hideSoftInput();
                mEtContent.clearFocus();
                return false;
            }
        });
        mEtContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && mRlBottomLayout.isShown()) {
                    hideBottomLayout();//隐藏表情布局，显示软件盘
                }
                return false;
            }
        });
    }

    @OnClick({R.id.btn_send, R.id.ivAdd, R.id.llTips1, R.id.llTips2, R.id.llTips3, R.id.llTips4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                showBottomLayout = false;
                sendTextMsg(mEtContent.getText().toString());
                mEtContent.setText("");
                break;
            case R.id.ivAdd:
                mEtContent.clearFocus();
                showBottomLayout = true;
                if (!isSoftInputShow) {
                    showBottomLayout();
                } else {
                    hideSoftInput();
                }
                break;
            case R.id.llTips1:
                sendTextMsg(tvTips1.getText().toString());
                hideBottomLayout();
                break;
            case R.id.llTips2:
                sendTextMsg(tvTips2.getText().toString());
                hideBottomLayout();
                break;
            case R.id.llTips3:
                sendTextMsg(tvTips3.getText().toString());
                hideBottomLayout();
                break;
            case R.id.llTips4:
                sendTextMsg(tvTips4.getText().toString());
                hideBottomLayout();
                break;
        }
    }

    //文本消息
    private void sendTextMsg(String msg) {
        Message mMessage = getBaseSendMessage();
        mMessage.setContent(msg);
        //开始发送
        mAdapter.addData(mMessage);
        //模拟两秒后发送成功
        updateMsg(mMessage);
    }

    private Message getBaseSendMessage() {
        Message mMessage = new Message();
        mMessage.setConversationToken(mTargetId);
        mMessage.setSendToken(mSenderId);
        mMessage.setReceiverToken(mTargetId);
        mMessage.setTime(System.currentTimeMillis());
        mMessage.setStatus(0);
        return mMessage;
    }

    private void updateMsg(final Message mMessage) {
        mRvChat.scrollToPosition(mAdapter.getItemCount() - 1);
        //模拟2秒后发送成功
        new Handler().postDelayed(new Runnable() {
            public void run() {
                int position = 0;
                mMessage.setStatus(1);
                //更新单个子条目
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    Message mAdapterMessage = mAdapter.getData().get(i);
                    if (mMessage.getSendToken().equals(mAdapterMessage.getSendToken())) {
                        position = i;
                    }
                }
                dao.insert(mMessage);

                conversation.setLastTime(mMessage.getTime());
                conversation.setLastMessage(mMessage.getContent());
                conversationDao.update(conversation);

                mAdapter.notifyItemChanged(position);
            }
        }, 0);
    }

    private void showBottomLayout() {
        mRlBottomLayout.setVisibility(View.VISIBLE);
        showBottomLayout = false;
    }

    /**
     * 隐藏底部布局
     */
    public void hideBottomLayout() {
        if (mRlBottomLayout.isShown()) {
            mRlBottomLayout.setVisibility(View.GONE);
        }
    }

    private InputMethodManager mInputManager;

    /**
     * 隐藏软件盘
     */
    public void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(mEtContent.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        if (mRlBottomLayout.getVisibility() == View.VISIBLE) {
            hideBottomLayout();
            return;
        }
        super.onBackPressed();
    }
}
