package com.applidium.graphql.client.di.common;

import com.applidium.graphql.client.core.boundary.GraphQLRepository;
import com.applidium.graphql.client.core.boundary.UserRepository;
import com.applidium.graphql.client.di.crashes.CrashesComponent;
import com.applidium.graphql.client.di.crashes.CrashesModule;
import com.applidium.graphql.client.di.logging.LoggingComponent;
import com.applidium.graphql.client.di.logging.LoggingModule;
import com.applidium.graphql.client.di.threading.ThreadingComponent;
import com.applidium.graphql.client.di.threading.ThreadingModule;
import com.applidium.graphql.client.di.trace.TracerModule;
import com.applidium.graphql.client.utils.logging.Logger;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    LoggingModule.class,
    PreferencesModule.class,
    ServiceModule.class,
    TracerModule.class,
    RepositoryModule.class
})
public interface ApplicationComponent {
    Logger logger();
    GraphQLRepository graphqlRepository();
    UserRepository userRepository();

    LoggingComponent.Builder loggingComponentBuilder();
    CrashesComponent plus(CrashesModule module);
    ThreadingComponent plus(ThreadingModule module);
}
