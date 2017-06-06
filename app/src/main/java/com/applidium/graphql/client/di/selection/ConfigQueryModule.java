package com.applidium.graphql.client.di.selection;

import com.applidium.graphql.client.app.selection.ui.fragment.CreateQueryViewContract;

import dagger.Module;
import dagger.Provides;

@Module
public class ConfigQueryModule {
    private final CreateQueryViewContract viewContract;

    public ConfigQueryModule(CreateQueryViewContract viewContract) {
        this.viewContract = viewContract;
    }

    @Provides
    CreateQueryViewContract viewContract() {
        return viewContract;
    }
}
