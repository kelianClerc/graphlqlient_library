package com.applidium.graphql.client.di.common;

import android.support.v4.app.FragmentManager;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentManagerModule {

    private final FragmentManager manager;

    public FragmentManagerModule(FragmentManager manager) {
        this.manager = manager;
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return manager;
    }
}
