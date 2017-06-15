package com.applidium.graphql.client.app.user.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.user.ui.UserViewContract;

import javax.inject.Inject;

public class UserPresenter extends Presenter<UserViewContract> {
    @Inject UserPresenter(UserViewContract view) {
        super(view);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
