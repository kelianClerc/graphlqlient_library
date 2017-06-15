package com.applidium.graphql.client.app.users.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.applidium.graphql.client.R;
import com.applidium.graphql.client.app.common.BaseActivity;
import com.applidium.graphql.client.app.common.DividerHorizontalItemDecoration;
import com.applidium.graphql.client.app.users.model.UserViewModel;
import com.applidium.graphql.client.app.users.presenter.UsersPresenter;
import com.applidium.graphql.client.app.users.ui.UsersViewContract;
import com.applidium.graphql.client.app.users.ui.adapter.UsersAdapter;
import com.applidium.graphql.client.di.ComponentManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class UsersActivity extends BaseActivity implements UsersViewContract, UsersAdapter.UserClickedListener {


    @BindView(R.id.user_list) RecyclerView recyclerView;

    @Inject UsersPresenter presenter;
    private UsersAdapter adapter;

    @Override
    protected void injectDependencies() {
        ComponentManager.getUsersComponent(this, this).inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);
        setupAdapter();
    }

    private void setupAdapter() {
        adapter = new UsersAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        DividerHorizontalItemDecoration decor =
            new DividerHorizontalItemDecoration(getContext(), R.drawable.divider_line);
        recyclerView.addItemDecoration(decor);
    }

    @Override
    public void onUserClicked(UserViewModel user) {
        presenter.userClicked(user.id());
    }

    @Override
    public void showUsers(List<UserViewModel> users) {
        adapter.setContentViewModels(users);
    }
}
