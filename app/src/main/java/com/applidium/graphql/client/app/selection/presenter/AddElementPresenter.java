package com.applidium.graphql.client.app.selection.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.selection.ui.fragment.CreateQueryViewContract;

import javax.inject.Inject;

public class AddElementPresenter extends Presenter<CreateQueryViewContract> {

    @Inject AddElementPresenter(CreateQueryViewContract view) {
        super(view);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
