package com.applidium.graphql.client.core.interactor.userposts;

public interface GetUserPostsListener {
    void onGetUserPostsSuccess(UserPostsResponse response);
    void onGetUserPostsError(String errorMessage);
}
