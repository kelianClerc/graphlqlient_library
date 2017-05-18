package com.applidium.graphql.client.di.threading;

import com.applidium.graphql.client.utils.threading.JobExecutor;
import com.applidium.graphql.client.utils.threading.PostExecutionThread;
import com.applidium.graphql.client.utils.threading.ThreadExecutor;
import com.applidium.graphql.client.utils.threading.UiThread;

import dagger.Module;
import dagger.Provides;

@Module
public class ThreadingModule {

    @Provides
    ThreadExecutor provideExecutor(JobExecutor instance) {
        return instance;
    }

    @Provides
    PostExecutionThread providePostThread(UiThread instance) {
        return instance;
    }
}
