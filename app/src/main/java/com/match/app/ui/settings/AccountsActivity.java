package com.match.app.ui.settings;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.match.app.base.BaseActivity;
import com.match.app.db.AccountDao;
import com.match.app.message.table.Account;
import com.matches.fitness.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountsActivity extends BaseActivity implements AccountAdapter.OnItemClickListener, AccountAdapter.OnItemLongClickListener {

    @BindView(R.id.rlLeftBack)
    RelativeLayout rlLeftBack;
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
        initTile();
        getAccount();
        accountsRcy.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AccountAdapter(this, accounts);
        accountsRcy.setAdapter(adapter);
        adapter.setItemClickListener(this);
        adapter.setItemLongClickListener(this);
    }

    private void initTile() {
        tvTitle.setText("账号管理");
        rlLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getAccount() {
        dao = new AccountDao(this);
        accounts = dao.queryAll();
        if (accounts != null && !accounts.isEmpty()) {
            for (int i = 0; i < accounts.size(); i++) {
                Account account = accounts.get(i);
                if (account.getAccount().equals(com.match.app.message.entity.User.getInstance().getLoginName())) {
                    accounts.remove(account);
                    accounts.add(0, account);
                    return;
                }
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, final int position) {

    }
}
