package com.applidium.graphql.client.app.user.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.user.model.UserViewModel;
import com.applidium.graphql.client.app.user.ui.UserViewContract;
import com.applidium.graphql.client.app.user.model.UserMapper;
import com.applidium.graphql.client.core.interactor.userposts.GetUserPostsInteractor;
import com.applidium.graphql.client.core.interactor.userposts.GetUserPostsListener;
import com.applidium.graphql.client.core.interactor.userposts.UserPostsResponse;

import javax.inject.Inject;

public class UserPresenter extends Presenter<UserViewContract> implements GetUserPostsListener {
    private final GetUserPostsInteractor interactor;
    private final UserMapper mapper;

    @Inject UserPresenter(UserViewContract view, GetUserPostsInteractor interactor, UserMapper
        mapper) {
        super(view);
        this.interactor = interactor;
        this.mapper = mapper;
    }

    public void onStart(String idTarget) {
        interactor.execute(idTarget, this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onGetUserPostsSuccess(UserPostsResponse response) {
        UserViewModel userViewModel = mapper.map(response.userResponse());
        view.showUserProfile(userViewModel);
    }

    @Override
    public void onGetUserPostsError(String errorMessage) {

    }
}
