package com.applidium.graphql.client.di.selection.addelement;

import com.applidium.graphql.client.app.selection.ui.fragment.AddElementFragment;
import com.applidium.graphql.client.di.PerActivity;
import com.applidium.graphql.client.di.common.ApplicationComponent;
import com.applidium.graphql.client.di.common.ContextModule;
import com.applidium.graphql.client.di.common.FragmentManagerModule;

import dagger.Component;

@PerActivity
@Component(
    modules = {AddElementModule.class, ContextModule.class, FragmentManagerModule.class},
    dependencies = ApplicationComponent.class
)
public interface AddElementComponent {
    void inject(AddElementFragment addElementFragment);
}
