package com.applidium.graphql.client.di.selection;

import com.applidium.graphql.client.app.selection.ui.activity.SelectionActivity;
import com.applidium.graphql.client.app.selection.ui.fragment.ConfigFragment;
import com.applidium.graphql.client.di.PerActivity;
import com.applidium.graphql.client.di.common.ApplicationComponent;
import com.applidium.graphql.client.di.common.ContextModule;
import com.applidium.graphql.client.di.common.FragmentManagerModule;

import dagger.Component;

@PerActivity
@Component(
    modules = {ConfigQueryModule.class, ContextModule.class, FragmentManagerModule.class},
    dependencies = ApplicationComponent.class
)
public interface ConfigQueryComponent {
    void inject(ConfigFragment fragment);
}
