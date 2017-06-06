package com.applidium.graphql.client.di.selection;

import com.applidium.graphql.client.app.selection.ui.fragment.ConfigViewContract;

import dagger.Module;
import dagger.Provides;

@Module
public class ConfigQueryModule {
    private final ConfigViewContract viewContract;

    public ConfigQueryModule(ConfigViewContract viewContract) {
        this.viewContract = viewContract;
    }

    @Provides
    ConfigViewContract viewContract() {
        return viewContract;
    }
}
