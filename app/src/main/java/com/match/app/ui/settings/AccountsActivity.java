package com.match.app.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.match.app.MyApp;
import com.match.app.common.User;
import com.match.app.db.AccountDao;
import com.match.app.message.entity.Account;
import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountsActivity extends BaseActivity implements AccountAdapter.OnItemClickListener, AccountAdapter.OnItemLongClickListener {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.accounts_rcy)
    RecyclerView accountsRcy;

    @BindView(R.id.ll_add)
    LinearLayout llAdd;
    private List<Account> accounts;
    private AccountDao dao;

    private AccountAdapter adapter;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_accounts);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        initTile("账号管理", true);
        getAccount();

        accountsRcy.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AccountAdapter(mContext, accounts);
        accountsRcy.setAdapter(adapter);
        adapter.setItemClickListener(this);
        adapter.setItemLongClickListener(this);
    }

    private void getAccount() {
        dao = new AccountDao(mContext);
        accounts = dao.queryAll();
        if (accounts != null && !accounts.isEmpty()) {
            for (int i = 0; i < accounts.size(); i++) {
                Account account = accounts.get(i);
                if (account.getAccount().equals(User.getInstance().getLoginName())) {
                    accounts.remove(account);
                    accounts.add(0, account);
                    return;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add:

                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position != 0) {
            Account account = accounts.get(position);
            MyApp.switchAccount(account, mContext);
        }
    }

    AlertDialog dialog;

    @Override
    public void onItemLongClick(View view, final int position) {
        dialog = new AlertDialog.Builder(mContext)
                .setTitle(TextUtils.isEmpty(accounts.get(position).getName()) ? accounts.get(position).getAccount() : accounts.get(position).getName())
                .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Account deleteAccount = accounts.get(position);
                        accounts.remove(position);
                        adapter.notifyDataSetChanged();
                        dao.delete(deleteAccount);
                        dialog.dismiss();

                        if (position == 0) {
                            User user = User.getInstance();
                            user.reset();
                            user.save();
                            MyApp.logout(mContext);
                        }
                    }
                }).create();
        dialog.show();
    }
}
