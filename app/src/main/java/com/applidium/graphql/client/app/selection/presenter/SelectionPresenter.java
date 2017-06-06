package com.applidium.graphql.client.app.selection.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.selection.ui.SelectionViewContract;

import javax.inject.Inject;

public class SelectionPresenter extends Presenter<SelectionViewContract> {

    @Inject
    SelectionPresenter(SelectionViewContract view) {
        super(view);
    }

    @Override
    public void start() {
        // TODO (kelianclerc) 6/6/17
    }

    @Override
    public void stop() {
        // TODO (kelianclerc) 6/6/17
    }

    public void onValidate() {
        // TODO (kelianclerc) 6/6/17
    }
}
