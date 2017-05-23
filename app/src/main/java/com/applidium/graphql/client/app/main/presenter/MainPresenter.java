package com.applidium.graphql.client.app.main.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.main.ui.MainViewContract;
import com.applidium.graphql.client.core.interactor.sendrequest.SendRequestInteractor;
import com.applidium.graphql.client.core.interactor.sendrequest.SendRequestListener;
import com.applidium.graphql.client.utils.trace.Trace;

import javax.inject.Inject;

public class MainPresenter extends Presenter<MainViewContract> implements SendRequestListener {

    private final SendRequestInteractor interactor;

    @Inject
    MainPresenter(MainViewContract view, SendRequestInteractor interactor) {
        super(view);
        this.interactor = interactor;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public void onLaunchRequest(String request) {
        interactor.execute(request, this);
    }

    @Override @Trace @Deprecated
    public void onSendRequestResponse(String response, String request) {
        view.showResponse(response);
        view.showRequest(request);
    }

    @Override @Trace @Deprecated
    public void onSendRequestError(String message) {

    }
}
