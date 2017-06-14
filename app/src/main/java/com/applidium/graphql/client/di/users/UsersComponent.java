package com.applidium.graphql.client.di.users;

import com.applidium.graphql.client.app.users.ui.activity.UsersActivity;
import com.applidium.graphql.client.di.PerActivity;
import com.applidium.graphql.client.di.common.ApplicationComponent;
import com.applidium.graphql.client.di.common.ContextModule;

import dagger.Component;

@PerActivity
@Component(
    modules = {UsersModule.class, ContextModule.class},
    dependencies = ApplicationComponent.class
)
public interface UsersComponent {
    void inject(UsersActivity activity);
}


