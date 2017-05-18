package com.applidium.graphql.client.utils.threading;

public interface PostExecutionThread {
    void post(Runnable runnable);
}
