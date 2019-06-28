package com.match.app.ui.im;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
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

import com.match.app.base.BaseActivity;
import com.match.app.customer.RecordButton;
import com.match.app.customer.StateButton;
import com.match.app.message.MsgSendStatus;
import com.match.app.message.MsgType;
import com.match.app.message.entity.MessageEntity;
import com.match.app.message.entity.TextMsgBody;
import com.match.app.ui.adapter.ChatsAdapter;
import com.match.app.utils.SoftKeyBoardListener;
import com.matches.fitness.R;

import java.util.ArrayList;
import java.util.UUID;

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
    @BindView(R.id.ivEmo)
    ImageView mIvEmo;
    @BindView(R.id.btn_send)
    StateButton mBtnSend;//发送按钮
    @BindView(R.id.ivAudio)
    ImageView mIvAudio;//录音图片
    @BindView(R.id.btnAudio)
    RecordButton mBtnAudio;//录音按钮
    @BindView(R.id.llAdd)
    LinearLayout mLlAdd;//添加布局

    private MutableLiveData<Boolean> softInputShow = new MutableLiveData<>();
    private Boolean isSoftInputShow = false;
    private Boolean showBottomLayout = false;

    private ChatsAdapter mAdapter;
    public static final String mSenderId = "right";
    public static final String mTargetId = "left";
    public static final int REQUEST_CODE_IMAGE = 0000;
    public static final int REQUEST_CODE_VEDIO = 1111;
    public static final int REQUEST_CODE_FILE = 2222;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_chat);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);

        initTile("用户", true);
        mAdapter = new ChatsAdapter(this, new ArrayList<MessageEntity>());
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(this);
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

        mEtContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && mRlBottomLayout.isShown()) {
                    hideBottomLayout();//隐藏表情布局，显示软件盘
                }
                return false;
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


    private void initChatUi() {
        //底部布局弹出,聊天列表上滑
        mRvChat.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    mRvChat.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mAdapter.getItemCount() > 0) {
                                mRvChat.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                            }
                        }
                    });
                }
            }
        });
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
    }

    @OnClick({R.id.btn_send, R.id.ivAdd})
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
                if (isSoftInputShow == false) {
                    showBottomLayout();
                } else {
                    hideSoftInput();
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_FILE:
                    break;
                case REQUEST_CODE_IMAGE:
                    break;
                case REQUEST_CODE_VEDIO:
                    break;
            }
        }
    }

    //文本消息
    private void sendTextMsg(String hello) {
        final MessageEntity mMessgae = getBaseSendMessage(MsgType.TEXT);
        TextMsgBody mTextMsgBody = new TextMsgBody();
        mTextMsgBody.setMessage(hello);
        mMessgae.setBody(mTextMsgBody);
        //开始发送
        mAdapter.addData(mMessgae);
        //模拟两秒后发送成功
        updateMsg(mMessgae);
    }


    private MessageEntity getBaseSendMessage(MsgType msgType) {
        MessageEntity mMessgae = new MessageEntity();
        mMessgae.setUuid(UUID.randomUUID() + "");
        mMessgae.setSenderId(mSenderId);
        mMessgae.setTargetId(mTargetId);
        mMessgae.setSentTime(System.currentTimeMillis());
        mMessgae.setSentStatus(MsgSendStatus.SENDING);
        mMessgae.setMsgType(msgType);
        return mMessgae;
    }


    private MessageEntity getBaseReceiveMessage(MsgType msgType) {
        MessageEntity mMessgae = new MessageEntity();
        mMessgae.setUuid(UUID.randomUUID() + "");
        mMessgae.setSenderId(mTargetId);
        mMessgae.setTargetId(mSenderId);
        mMessgae.setSentTime(System.currentTimeMillis());
        mMessgae.setSentStatus(MsgSendStatus.SENDING);
        mMessgae.setMsgType(msgType);
        return mMessgae;
    }


    private void updateMsg(final MessageEntity mMessgae) {
        mRvChat.scrollToPosition(mAdapter.getItemCount() - 1);
        //模拟2秒后发送成功
        new Handler().postDelayed(new Runnable() {
            public void run() {
                int position = 0;
                mMessgae.setSentStatus(MsgSendStatus.SENT);
                //更新单个子条目
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    MessageEntity mAdapterMessage = mAdapter.getData().get(i);
                    if (mMessgae.getUuid().equals(mAdapterMessage.getUuid())) {
                        position = i;
                    }
                }
                mAdapter.notifyItemChanged(position);
            }
        }, 2000);

    }

    private void showBottomLayout() {
        mRlBottomLayout.setVisibility(View.VISIBLE);
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

}
