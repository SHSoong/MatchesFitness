package com.match.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.match.app.message.table.User;
import com.matches.fitness.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountHolder> {

    private List<User> users;

    @Override
    public AccountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(AccountHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    class AccountHolder extends RecyclerView.ViewHolder {
        private CircleImageView headerImg;
        private TextView tvMessage;
        private ImageView stateView;

        public AccountHolder(View itemView) {
            super(itemView);
            headerImg = itemView.findViewById(R.id.cimg_header);
            tvMessage = itemView.findViewById(R.id.tv_message);
        }
    }

}
