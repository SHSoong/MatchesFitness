package com.match.app.ui.settings;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.match.app.common.User;
import com.match.app.db.AccountDao;
import com.match.app.db.TbAccount;
import com.match.app.ui.adapter.ChatAdapter;
import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountsActivity extends BaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.accounts_rcy)
    RecyclerView accountsRcy;
    private List<TbAccount> accounts;
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
    }

    private void getAccount() {
        dao = new AccountDao(mContext);
        accounts = dao.queryAll();
        if (accounts != null && !accounts.isEmpty()) {
            for (TbAccount account : accounts) {
                if (account.getAccount().equals(User.getInstance().getLoginName())) {
                    accounts.remove(account);
                    accounts.add(0, account);
                }
            }
        }
    }

}
