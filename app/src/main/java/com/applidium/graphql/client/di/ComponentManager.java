package com.applidium.graphql.client.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.applidium.graphql.client.app.main.ui.MainViewContract;
import com.applidium.graphql.client.app.user.ui.UserViewContract;
import com.applidium.graphql.client.app.users.ui.UsersViewContract;
import com.applidium.graphql.client.di.common.ApplicationComponent;
import com.applidium.graphql.client.di.common.ContextModule;
import com.applidium.graphql.client.di.common.DaggerApplicationComponent;
import com.applidium.graphql.client.di.common.PreferencesModule;
import com.applidium.graphql.client.di.common.RepositoryModule;
import com.applidium.graphql.client.di.common.ServiceModule;
import com.applidium.graphql.client.di.crashes.CrashesComponent;
import com.applidium.graphql.client.di.crashes.CrashesModule;
import com.applidium.graphql.client.di.logging.LoggingComponent;
import com.applidium.graphql.client.di.logging.LoggingModule;
import com.applidium.graphql.client.di.main.DaggerMainComponent;
import com.applidium.graphql.client.di.main.MainComponent;
import com.applidium.graphql.client.di.main.MainModule;
import com.applidium.graphql.client.di.threading.ThreadingComponent;
import com.applidium.graphql.client.di.threading.ThreadingModule;
import com.applidium.graphql.client.di.trace.TracerModule;
import com.applidium.graphql.client.di.user.DaggerUserDetailComponent;
import com.applidium.graphql.client.di.user.UserDetailComponent;
import com.applidium.graphql.client.di.user.UserModule;
import com.applidium.graphql.client.di.users.DaggerUsersComponent;
import com.applidium.graphql.client.di.users.UsersComponent;
import com.applidium.graphql.client.di.users.UsersModule;

import java.io.File;

public class ComponentManager {

    private static ApplicationComponent applicationComponent;
    private static LoggingComponent loggingComponent;
    private static ThreadingComponent threadingComponent;
    private static CrashesComponent crashesComponent;

    public static void init(SharedPreferences preferences, File cacheDirectory) {
        LoggingModule loggingModule = new LoggingModule();
        PreferencesModule preferencesModule = new PreferencesModule(preferences);
        ServiceModule serviceModule = new ServiceModule(cacheDirectory);
        TracerModule tracerModule = new TracerModule();
        RepositoryModule repositoryModule = new RepositoryModule();
        initApplicationComponent(
            loggingModule,
            preferencesModule,
            serviceModule,
            tracerModule,
            repositoryModule
        );
        initLoggingComponent();
        ThreadingModule threadingModule = new ThreadingModule();
        initThreadingComponent(threadingModule);
        CrashesModule crashesModule = new CrashesModule();
        initCrashesComponent(crashesModule);
    }

    public static ApplicationComponent getApplicationComponent() {
        return safeReturn(applicationComponent);
    }

    public static LoggingComponent getLoggingComponent() {
        return safeReturn(loggingComponent);
    }

    public static ThreadingComponent getThreadingComponent() {
        return safeReturn(threadingComponent);
    }

    public static CrashesComponent getCrashesComponent() {
        return safeReturn(crashesComponent);
    }

    private static void initApplicationComponent(
        LoggingModule loggingModule,
        PreferencesModule preferencesModule,
        ServiceModule serviceModule,
        TracerModule tracerModule,
        RepositoryModule repositoryModule
    ) {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .loggingModule(loggingModule)
            .tracerModule(tracerModule)
            .preferencesModule(preferencesModule)
            .serviceModule(serviceModule)
            .repositoryModule(repositoryModule)
            .build();
    }

    private static void initLoggingComponent() {
        loggingComponent = applicationComponent.loggingComponentBuilder().build();
    }

    private static void initThreadingComponent(ThreadingModule threadingModule) {
        threadingComponent = applicationComponent.plus(threadingModule);
    }

    private static void initCrashesComponent(CrashesModule crashesModule) {
        crashesComponent = applicationComponent.plus(crashesModule);
    }

    @NonNull
    private static <C> C safeReturn(C component) {
        if (component == null) {
            fail();
        }
        return component;
    }

    private static void fail() {
        String message = "ComponentManager.init() was not called on Application#onCreate()";
        throw new RuntimeException(message);
    }

    public static MainComponent getMainComponent(MainViewContract viewContract, Context context) {
        return DaggerMainComponent
            .builder()
            .applicationComponent(getApplicationComponent())
            .mainModule(new MainModule(viewContract))
            .contextModule(new ContextModule(context))
            .build();
    }

    public static UsersComponent getUsersComponent(UsersViewContract viewContract, Context context) {
        return DaggerUsersComponent
            .builder()
            .applicationComponent(getApplicationComponent())
            .usersModule(new UsersModule(viewContract))
            .contextModule(new ContextModule(context))
            .build();
    }

    public static UserDetailComponent userDetailComponent(UserViewContract viewContract, Context context) {
        return DaggerUserDetailComponent
            .builder()
            .applicationComponent(getApplicationComponent())
            .userModule(new UserModule(viewContract))
            .contextModule(new ContextModule(context))
            .build();
    }
}
