package com.applidium.graphql.client.di.selection;

import com.applidium.graphql.client.app.selection.ui.SelectionViewContract;

import dagger.Module;
import dagger.Provides;

@Module
public class SelectionModule {
    private final SelectionViewContract viewContract;

    public SelectionModule(SelectionViewContract viewContract) {
        this.viewContract = viewContract;
    }

    @Provides
    SelectionViewContract viewContract() {
        return viewContract;
    }
}
