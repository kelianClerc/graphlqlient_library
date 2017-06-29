package com.applidium.graphql.client.di.main;

import com.applidium.graphql.client.app.main.ui.MainViewContract;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    private final MainViewContract viewContract;

    public MainModule(MainViewContract viewContract) {
        this.viewContract = viewContract;
    }

    @Provides
    MainViewContract viewContract() {
        return viewContract;
    }
}
