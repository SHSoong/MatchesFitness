package com.match.app.ui.settings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.match.app.message.table.Account;
import com.matches.fitness.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountHolder> {

    private List<Account> lists;
    private Context mContext;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public AccountAdapter(Context context, List<Account> lists) {
        this.mContext = context;
        this.lists = lists;
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }


    @Override
    public AccountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itemview_account, parent, false);

        return new AccountHolder(view);
    }

    @Override
    public void onBindViewHolder(final AccountHolder holder, final int position) {
        Account account = lists.get(position);
        if (position == 0) {
            holder.imgState.setVisibility(View.VISIBLE);
        } else {
            holder.imgState.setVisibility(View.GONE);
        }

        if (position == getItemCount() - 1) {
            holder.line.setVisibility(View.GONE);
        }

        holder.tvAccount.setText(TextUtils.isEmpty(account.getName()) ? account.getAccount() : account.getName());
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.icon_avatar);
        Glide.with(mContext)
                .load(account.getLogo())
                .apply(options)
                .into(holder.headerImg);

        //判断是否设置了监听器
        if(itemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(holder.itemView,position); // 2
                }
            });
        }
        if(onItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClickListener.onItemLongClick(holder.itemView,position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    class AccountHolder extends RecyclerView.ViewHolder {
        private CircleImageView headerImg;
        private TextView tvAccount;
        private ImageView imgState;
        private View line;

        public AccountHolder(View itemView) {
            super(itemView);
            headerImg = itemView.findViewById(R.id.cimg_header);
            tvAccount = itemView.findViewById(R.id.tv_account);
            imgState = itemView.findViewById(R.id.img_state);
            line = itemView.findViewById(R.id.line);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
