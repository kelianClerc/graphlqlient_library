package com.applidium.graphql.client.core.interactor.listofusers;

public interface GetListUsersListener {
    void onGetListOfUsersSuccess(ListUsersResponse response);
    void onGetListOfUsersError(String errorMessage);
}
