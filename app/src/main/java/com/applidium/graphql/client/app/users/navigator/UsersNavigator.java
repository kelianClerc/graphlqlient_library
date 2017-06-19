package com.applidium.graphql.client.app.users.navigator;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;

import com.applidium.graphql.client.R;
import com.applidium.graphql.client.app.user.ui.activity.UserActivity;

import javax.inject.Inject;

public class UsersNavigator {
    private final Context context;

    @Inject UsersNavigator(Context context) {
        this.context = context;
    }

    public void navigateToUser(String id) {
        Intent intent = UserActivity.makeIntent(context, id);

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(
            context, R.anim.slide_in, android.R.anim.fade_out);
        context.startActivity(intent, optionsCompat.toBundle());
    }
}
