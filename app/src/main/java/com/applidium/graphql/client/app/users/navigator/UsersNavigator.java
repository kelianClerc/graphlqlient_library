package com.applidium.graphql.client.app.users.navigator;

import android.content.Context;

import javax.inject.Inject;

public class UsersNavigator {
    private final Context context;

    @Inject UsersNavigator(Context context) {
        this.context = context;
    }

    public void navigateToUser(String id) {

    }
}
