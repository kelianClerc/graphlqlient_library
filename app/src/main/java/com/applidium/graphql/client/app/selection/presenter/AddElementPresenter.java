package com.applidium.graphql.client.app.selection.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.selection.ui.fragment.AddElementViewContract;
import com.applidium.graphql.client.app.selection.ui.fragment.CreateQueryViewContract;

import javax.inject.Inject;

public class AddElementPresenter extends Presenter<AddElementViewContract> {

    @Inject AddElementPresenter(AddElementViewContract view) {
        super(view);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
