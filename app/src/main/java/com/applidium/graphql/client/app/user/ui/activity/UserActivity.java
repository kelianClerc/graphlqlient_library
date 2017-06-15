package com.applidium.graphql.client.app.user.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.applidium.graphql.client.R;
import com.applidium.graphql.client.app.common.BaseActivity;
import com.applidium.graphql.client.app.common.DividerHorizontalItemDecoration;
import com.applidium.graphql.client.app.user.model.PostViewModel;
import com.applidium.graphql.client.app.user.presenter.UserPresenter;
import com.applidium.graphql.client.app.user.ui.UserViewContract;
import com.applidium.graphql.client.app.user.ui.adapter.PostAdapter;
import com.applidium.graphql.client.di.ComponentManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class UserActivity extends BaseActivity implements UserViewContract, PostAdapter.PostClickedListener {

    @BindView(R.id.name) TextView name;
    @BindView(R.id.email) TextView email;
    @BindView(R.id.since) TextView since;
    @BindView(R.id.recycler) RecyclerView recyclerView;
    private PostAdapter adapter;

    @Inject UserPresenter presenter;

    @Override
    protected void injectDependencies() {
        ComponentManager.userDetailComponent(this, this).inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        setupAdapter();
    }

    private void setupAdapter() {
        adapter = new PostAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        DividerHorizontalItemDecoration decor =
            new DividerHorizontalItemDecoration(getContext(), R.drawable.divider_line);
        recyclerView.addItemDecoration(decor);
    }

    @Override
    public void showUserProfile(com.applidium.graphql.client.app.user.model.UserViewModel userViewModel) {
        name.setText(userViewModel.name());
        email.setText(userViewModel.email());
        since.setText(userViewModel.memberSince());
        adapter.setContentViewModels(userViewModel.posts());
    }

    @Override
    public void onCommentClicked(PostViewModel postViewModel) {
        // TODO (kelianclerc) 15/6/17
    }

    @Override
    public void onVotesClicked(PostViewModel postViewModel) {
// TODO (kelianclerc) 15/6/17
    }
}
