package com.applidium.graphql.client.app.users.ui;

import com.applidium.graphql.client.app.common.ViewContract;
import com.applidium.graphql.client.app.users.model.UserViewModel;

import java.util.List;

public interface UsersViewContract extends ViewContract {
    void showUsers(List<UserViewModel> users);

    void showError(String errorMessage);
}
