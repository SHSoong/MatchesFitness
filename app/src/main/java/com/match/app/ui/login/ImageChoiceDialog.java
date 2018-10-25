package com.match.app.ui.login;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.TextView;

import com.matches.fitness.R;

/*****
 *
 * 选择照片的来源   本地选取还是拍照
 */
public class ImageChoiceDialog implements View.OnClickListener {

    private Context mContext;

    private BottomSheetDialog dialog;
    private TextView tvSelect;
    private TextView tvPhone;
    private TextView tvCancle;
    private boolean isCancelable;

    private View.OnClickListener listener;


    private ImageChoiceDialog(Context context) {
        mContext = context;
        dialog = new BottomSheetDialog(context);
        create();
    }

    private void create() {
        View view = View.inflate(mContext, R.layout.dialog_picuter_select, null);
        tvSelect = view.findViewById(R.id.tv_select);
        tvPhone = view.findViewById(R.id.tv_photo);
        tvCancle = view.findViewById(R.id.tv_cancle);
        tvSelect.setOnClickListener(this);
        tvPhone.setOnClickListener(this);
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
            case R.id.tv_select:
            case R.id.tv_photo:
                if (listener != null) {
                    dialog.dismiss();
                    listener.onClick(view);
                }
                break;
            case R.id.tv_cancle:
                dialog.dismiss();
                break;
        }
    }


    public static class Builder {
        private ImageChoiceDialog dialog;

        public Builder(Context context) {
            dialog = new ImageChoiceDialog(context);
        }

        public Builder setCancelable(boolean cancelable) {
            dialog.isCancelable = cancelable;
            return this;
        }

        public Builder setOnClickListener(View.OnClickListener listener) {
            dialog.listener = listener;
            return this;
        }


        public ImageChoiceDialog create() {
            dialog.show();
            return dialog;
        }


    }

}
