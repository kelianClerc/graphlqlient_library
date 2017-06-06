package com.applidium.graphql.client.app.main.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.main.navigator.MainNavigator;
import com.applidium.graphql.client.app.main.ui.MainViewContract;
import com.applidium.graphql.client.core.interactor.sendrequest.SendRequestInteractor;
import com.applidium.graphql.client.core.interactor.sendrequest.SendRequestListener;
import com.applidium.graphql.client.utils.trace.Trace;

import javax.inject.Inject;

public class MainPresenter extends Presenter<MainViewContract> implements SendRequestListener {

    private final SendRequestInteractor interactor;
    private final MainNavigator mainNavigator;

    @Inject
    MainPresenter(MainViewContract view, SendRequestInteractor interactor, MainNavigator
        mainNavigator) {
        super(view);
        this.interactor = interactor;
        this.mainNavigator = mainNavigator;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public void onLaunchRequest(String request, String variables) {
        interactor.execute(request, variables, this);
    }

    @Override @Trace @Deprecated
    public void onSendRequestResponse(String response, String request, String variables) {
        view.showResponse(response);
        view.showRequest(request);
        view.showVariables(variables);
    }

    @Override @Trace @Deprecated
    public void onSendRequestError(String message) {
        // TODO (kelianclerc) 1/6/17
    }

    public void onSettings() {
        mainNavigator.navigateToSettings();
    }
}
