package com.applidium.graphql.client.di.main;

import com.applidium.graphql.client.app.main.ui.activity.MainActivity;
import com.applidium.graphql.client.di.PerActivity;
import com.applidium.graphql.client.di.common.ApplicationComponent;
import com.applidium.graphql.client.di.common.ContextModule;

import dagger.Component;

@PerActivity
@Component(
    modules = {MainModule.class, ContextModule.class},
    dependencies = ApplicationComponent.class
)
public interface MainComponent {
    void inject(MainActivity fragment);
}

