package com.applidium.graphql.client.app.selection.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.selection.ui.fragment.ConfigViewContract;

import javax.inject.Inject;

public class ConfigPresenter extends Presenter<ConfigViewContract> {

    @Inject
    ConfigPresenter(ConfigViewContract view) {
        super(view);
    }

    @Override
    public void start() {
        view.createQuery();
    }

    @Override
    public void stop() {

    }
}
