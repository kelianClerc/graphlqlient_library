package com.applidium.graphql.client.app.user.ui;

import com.applidium.graphql.client.app.common.ViewContract;
import com.applidium.graphql.client.app.user.model.UserViewModel;

public interface UserViewContract extends ViewContract {
    void showUserProfile(UserViewModel userViewModel);

    void showError(String errorMessage);
}
