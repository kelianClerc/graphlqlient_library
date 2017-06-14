package com.applidium.graphql.client.app.users.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.users.ui.UsersViewContract;

import javax.inject.Inject;

public class UsersPresenter extends Presenter<UsersViewContract> {
    @Inject UsersPresenter(UsersViewContract view) {
        super(view);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
