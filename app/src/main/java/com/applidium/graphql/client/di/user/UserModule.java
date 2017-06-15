package com.applidium.graphql.client.di.user;

import com.applidium.graphql.client.app.user.ui.UserViewContract;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {
    private final UserViewContract viewContract;

    public UserModule(UserViewContract viewContract) {
        this.viewContract = viewContract;
    }

    @Provides
    UserViewContract viewContract() {
        return viewContract;
    }
}
