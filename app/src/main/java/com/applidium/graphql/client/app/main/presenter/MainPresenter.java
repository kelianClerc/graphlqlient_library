package com.applidium.graphql.client.app.main.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.main.ui.MainViewContract;
import com.applidium.graphql.client.core.interactor.sendrequest.SendRequestInteractor;
import com.applidium.graphql.client.core.interactor.sendrequest.SendRequestListener;

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

    @Override
    public void onSendRequestResponse(String response) {
        view.showResponse(response);
    }

    @Override
    public void onSendRequestError(String message) {

    }
}
