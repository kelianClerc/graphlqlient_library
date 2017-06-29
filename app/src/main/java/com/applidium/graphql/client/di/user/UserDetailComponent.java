package com.applidium.graphql.client.di.user;

import com.applidium.graphql.client.app.user.ui.activity.UserActivity;
import com.applidium.graphql.client.di.PerActivity;
import com.applidium.graphql.client.di.common.ApplicationComponent;
import com.applidium.graphql.client.di.common.ContextModule;

import dagger.Component;

@PerActivity
@Component(
    modules = {UserModule.class, ContextModule.class},
    dependencies = ApplicationComponent.class
)
public interface UserDetailComponent {
    void inject(UserActivity activity);
}
