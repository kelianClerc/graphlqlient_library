package com.applidium.graphql.client.di.common;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class PackageNameModule {

    private final String packageName;

    public PackageNameModule(String packageName) {
        this.packageName = packageName;
    }

    @Provides @Named("packageName")
    String providePackageName() {
        return packageName;
    }
}
