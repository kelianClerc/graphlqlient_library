package com.applidium.graphql.client.app.users.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.users.ui.UsersViewContract;
import com.applidium.graphql.client.core.interactor.listofusers.GetListOfUsersInteractor;
import com.applidium.graphql.client.core.interactor.listofusers.GetListUsersListener;
import com.applidium.graphql.client.core.interactor.listofusers.ListUsersResponse;

import javax.inject.Inject;

public class UsersPresenter extends Presenter<UsersViewContract> implements GetListUsersListener {

    private final GetListOfUsersInteractor interactor;

    @Inject UsersPresenter(UsersViewContract view, GetListOfUsersInteractor interactor) {
        super(view);
        this.interactor = interactor;
    }

    @Override
    public void start() {
        interactor.execute(this);

    }

    @Override
    public void stop() {

    }

    @Override
    public void onGetListOfUsersSuccess(ListUsersResponse response) {

    }

    @Override
    public void onGetListOfUsersError(String errorMessage) {

    }
}
