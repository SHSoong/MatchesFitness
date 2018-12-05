package com.match.app.utils;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.TextView;

import com.matches.fitness.R;

/*****
 *
 * 分析
 */
public class ShareDialog implements View.OnClickListener {

    private Context mContext;

    private BottomSheetDialog dialog;
    private TextView tvWx;
    private TextView tvPyq;
    private TextView tvWb;
    private TextView tvCopy;
    private TextView tvJb;
    private TextView tvCancle;
    private boolean isCancelable;

    private View.OnClickListener listener;


    private ShareDialog(Context context) {
        mContext = context;
        dialog = new BottomSheetDialog(context);
        create();
    }

    private void create() {
        View view = View.inflate(mContext, R.layout.dialog_share, null);
        tvWx = view.findViewById(R.id.tv_wx);
        tvPyq = view.findViewById(R.id.tv_pyq);
        tvWb = view.findViewById(R.id.tv_wb);
        tvCopy = view.findViewById(R.id.tv_copy);
        tvJb = view.findViewById(R.id.tv_jb);
        tvCancle = view.findViewById(R.id.tv_cancle);
        tvWx.setOnClickListener(this);
        tvPyq.setOnClickListener(this);
        tvWb.setOnClickListener(this);
        tvCopy.setOnClickListener(this);
        tvJb.setOnClickListener(this);
        tvCancle.setOnClickListener(this);
        dialog.setContentView(view);
        dialog.setCancelable(isCancelable);
    }

    public void show() {
        dialog.show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_cancle:
                dialog.dismiss();
                break;
        }
    }


    public static class Builder {
        private ShareDialog dialog;

        public Builder(Context context) {
            dialog = new ShareDialog(context);
        }

        public Builder setCancelable(boolean cancelable) {
            dialog.isCancelable = cancelable;
            return this;
        }

        public Builder setOnClickListener(View.OnClickListener listener) {
            dialog.listener = listener;
            return this;
        }


        public ShareDialog create() {
            dialog.show();
            return dialog;
        }


    }

}
