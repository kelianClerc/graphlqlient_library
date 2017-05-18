package com.applidium.graphql.client.di.crashes;

import android.app.Application;
import android.content.ComponentCallbacks2;

import net.hockeyapp.android.CrashManagerListener;

import javax.inject.Named;

import dagger.Subcomponent;

@Subcomponent(modules = {
    CrashesModule.class
})
public interface CrashesComponent {
    @Named("crash") CrashManagerListener crashesListener();
    Application.ActivityLifecycleCallbacks activityListener();
    ComponentCallbacks2 componentListener();
}
