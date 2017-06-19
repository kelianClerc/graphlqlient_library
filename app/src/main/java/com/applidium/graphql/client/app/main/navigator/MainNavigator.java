package com.applidium.graphql.client.app.main.navigator;

import android.content.Context;

import javax.inject.Inject;

public class MainNavigator {
    private Context context;

    @Inject MainNavigator(Context context) {
        this.context = context;
    }

    public void navigateToSettings() {
    }
}
