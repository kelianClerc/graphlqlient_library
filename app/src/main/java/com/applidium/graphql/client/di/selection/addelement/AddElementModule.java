package com.applidium.graphql.client.di.selection.addelement;

import com.applidium.graphql.client.app.selection.ui.fragment.AddElementViewContract;

import dagger.Module;
import dagger.Provides;

@Module
public class AddElementModule {
    private final AddElementViewContract viewContract;

    public AddElementModule(AddElementViewContract viewContract) {
        this.viewContract = viewContract;
    }

    @Provides
    AddElementViewContract viewContract() {
        return viewContract;
    }
}
