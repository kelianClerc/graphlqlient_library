package com.applidium.graphql.client.app.users.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.users.model.UserMapper;
import com.applidium.graphql.client.app.users.model.UserViewModel;
import com.applidium.graphql.client.app.users.navigator.UsersNavigator;
import com.applidium.graphql.client.app.users.ui.UsersViewContract;
import com.applidium.graphql.client.core.interactor.listofusers.GetListOfUsersInteractor;
import com.applidium.graphql.client.core.interactor.listofusers.GetListUsersListener;
import com.applidium.graphql.client.core.interactor.listofusers.ListUsersResponse;

import java.util.List;

import javax.inject.Inject;

public class UsersPresenter extends Presenter<UsersViewContract> implements GetListUsersListener {

    private final GetListOfUsersInteractor interactor;
    private final UserMapper mapper;
    private final UsersNavigator navigator;

    @Inject UsersPresenter(
        UsersViewContract view, GetListOfUsersInteractor interactor,
        UserMapper mapper, UsersNavigator navigator) {
        super(view);
        this.interactor = interactor;
        this.mapper = mapper;
        this.navigator = navigator;
    }

    @Override
    public void start() {
        interactor.execute(this);

    }

    @Override
    public void stop() {

    }

    @Override
    public void onGetListOfUsersSuccess(List<ListUsersResponse> response) {
        List<UserViewModel> viewModel = mapper.mapList(response);
        view.showUsers(viewModel);
    }

    @Override
    public void onGetListOfUsersError(String errorMessage) {

    }

    public void userClicked(String id) {
        navigator.navigateToUser(id);
    }
}
