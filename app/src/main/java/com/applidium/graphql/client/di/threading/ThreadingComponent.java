package com.applidium.graphql.client.di.threading;

import com.applidium.graphql.client.utils.aspect.ThreadingAspect;

import dagger.Subcomponent;

@Subcomponent(modules = ThreadingModule.class)
public interface ThreadingComponent {
    void inject(ThreadingAspect aspect);
}
