package com.applidium.graphql.client.app.main.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.main.ui.MainViewContract;

import javax.inject.Inject;

public class MainPresenter extends Presenter<MainViewContract> {

    @Inject
    MainPresenter(MainViewContract view) {
        super(view);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public void onLaunchRequest() {

    }
}
