package com.applidium.graphql.client.di.common;

import com.applidium.graphql.client.core.boundary.GraphQLRepository;
import com.applidium.graphql.client.core.boundary.UserRepository;
import com.applidium.graphql.client.data.net.ServiceGraphQLRepository;
import com.applidium.graphql.client.data.net.ServiceUserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    @Singleton
    GraphQLRepository provideReadNewsRepository(ServiceGraphQLRepository instance) {
        return instance;
    }
    @Provides
    @Singleton
    UserRepository provideUserRepository(ServiceUserRepository instance) {
        return instance;
    }
}
