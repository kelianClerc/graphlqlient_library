package com.applidium.graphql.client.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.applidium.graphql.client.app.main.ui.MainViewContract;
import com.applidium.graphql.client.app.selection.ui.SelectionViewContract;
import com.applidium.graphql.client.app.selection.ui.fragment.AddElementViewContract;
import com.applidium.graphql.client.app.selection.ui.fragment.CreateQueryViewContract;
import com.applidium.graphql.client.di.common.ApplicationComponent;
import com.applidium.graphql.client.di.common.ContextModule;
import com.applidium.graphql.client.di.common.DaggerApplicationComponent;
import com.applidium.graphql.client.di.common.FragmentManagerModule;
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
import com.applidium.graphql.client.di.selection.ConfigQueryComponent;
import com.applidium.graphql.client.di.selection.ConfigQueryModule;
import com.applidium.graphql.client.di.selection.DaggerConfigQueryComponent;
import com.applidium.graphql.client.di.selection.DaggerSelectionComponent;
import com.applidium.graphql.client.di.selection.SelectionComponent;
import com.applidium.graphql.client.di.selection.SelectionModule;
import com.applidium.graphql.client.di.selection.addelement.AddElementComponent;
import com.applidium.graphql.client.di.selection.addelement.AddElementModule;
import com.applidium.graphql.client.di.selection.addelement.DaggerAddElementComponent;
import com.applidium.graphql.client.di.threading.ThreadingComponent;
import com.applidium.graphql.client.di.threading.ThreadingModule;
import com.applidium.graphql.client.di.trace.TracerModule;

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

    public static SelectionComponent getSelectionComponent(SelectionViewContract viewContract, Context context, FragmentManager manager) {
        FragmentManagerModule module = new FragmentManagerModule(manager);
        return DaggerSelectionComponent
            .builder()
            .applicationComponent(getApplicationComponent())
            .selectionModule(new SelectionModule(viewContract))
            .fragmentManagerModule(module)
            .contextModule(new ContextModule(context))
            .build();
    }

    public static ConfigQueryComponent getConfigQueryComponent(
        CreateQueryViewContract viewContract, Context context, FragmentManager manager
    ) {
        return DaggerConfigQueryComponent
            .builder()
            .applicationComponent(getApplicationComponent())
            .configQueryModule(new ConfigQueryModule(viewContract))
            .fragmentManagerModule(new FragmentManagerModule(manager))
            .contextModule(new ContextModule(context))
            .build();
    }

    public static AddElementComponent getAddElementComponent
        (AddElementViewContract viewContract,
         Context context,
         FragmentManager fm
        ) {
        return DaggerAddElementComponent
            .builder()
            .applicationComponent(getApplicationComponent())
            .addElementModule(new AddElementModule(viewContract))
            .fragmentManagerModule(new FragmentManagerModule(fm))
            .contextModule(new ContextModule(context))
            .build();
    }
}
