package com.applidium.graphql.client.core.interactor.listofusers;

import java.util.List;

public interface GetListUsersListener {
    void onGetListOfUsersSuccess(List<ListUsersResponse> response);
    void onGetListOfUsersError(String errorMessage);
}
