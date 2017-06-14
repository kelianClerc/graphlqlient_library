package com.applidium.graphql.client.di.users;

import com.applidium.graphql.client.app.users.ui.UsersViewContract;

import dagger.Module;
import dagger.Provides;

@Module
public class UsersModule {
    private final UsersViewContract viewContract;

    public UsersModule(UsersViewContract viewContract) {
        this.viewContract = viewContract;
    }

    @Provides
    UsersViewContract viewContract() {
        return viewContract;
    }
}
