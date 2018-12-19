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
import com.match.app.db.TbAccount;
import com.match.app.ui.adapter.ChatAdapter;
import com.matches.fitness.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountHolder> {

    private List<TbAccount> lists;
    private Context mContext;

    public AccountAdapter(Context context, List<TbAccount> lists) {
        this.mContext = context;
        this.lists = lists;
    }


    @Override
    public AccountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itemview_account, parent, false);

        return new AccountHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountHolder holder, int position) {
        TbAccount account = lists.get(position);
        if (position == 0) {
            holder.imgState.setVisibility(View.VISIBLE);
        } else {
            holder.imgState.setVisibility(View.GONE);
        }

        if (position == getItemCount() - 1) {
            holder.line.setVisibility(View.GONE);
        }

        holder.tvAccount.setText(TextUtils.isEmpty(account.getName()) ? account.getAccount() : account.getName());
        Glide.with(mContext)
                .load(account.getLogo())
                .placeholder(R.mipmap.anim_avitor)
                .error(R.mipmap.anim_avitor)
                .dontAnimate()
                .into(holder.headerImg);

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
}
