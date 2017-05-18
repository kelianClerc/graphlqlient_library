package com.applidium.graphql.client.di.common;

import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;

@Module
public class ResourcesModule {

    private final Resources resources;

    public ResourcesModule(Resources resources) {
        this.resources = resources;
    }

    @Provides
    Resources provideResources() {
        return resources;
    }
}
