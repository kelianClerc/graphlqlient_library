package com.applidium.graphql.client.app.main.navigator;

import android.content.Context;
import android.content.Intent;

import com.applidium.graphql.client.app.selection.ui.activity.SelectionActivity;

import javax.inject.Inject;

public class MainNavigator {
    private Context context;

    @Inject MainNavigator(Context context) {
        this.context = context;
    }

    public void navigateToSettings() {
        Intent intent = SelectionActivity.makeIntent(context);
        context.startActivity(intent);
    }
}
