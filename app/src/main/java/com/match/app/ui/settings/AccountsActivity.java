package com.match.app.ui.settings;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.match.app.base.BaseActivity;
import com.match.app.db.AccountDao;
import com.match.app.message.table.User;
import com.matches.fitness.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountsActivity extends BaseActivity implements AccountAdapter.OnItemClickListener, AccountAdapter.OnItemLongClickListener {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rlLeftBack)
    RelativeLayout rlLeftBack;

    @BindView(R.id.accounts_rcy)
    RecyclerView accountsRcy;

    @BindView(R.id.ll_add)
    LinearLayout llAdd;
    private List<User> users;
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
        adapter = new AccountAdapter(mContext, users);
        accountsRcy.setAdapter(adapter);
        adapter.setItemClickListener(this);
        adapter.setItemLongClickListener(this);
    }

    private void getAccount() {
        dao = new AccountDao(mContext);
        users = dao.queryAll();
        if (users != null && !users.isEmpty()) {
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getAccount().equals(com.match.app.message.entity.User.getInstance().getLoginName())) {
                    users.remove(user);
                    users.add(0, user);
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

    }

    @Override
    public void onItemLongClick(View view, final int position) {

    }
}
