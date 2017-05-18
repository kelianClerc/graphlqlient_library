package com.applidium.graphql.client.app.common;

public abstract class Presenter<T extends ViewContract> {
    protected final T view;

    protected Presenter(T view) {
        this.view = view;
    }

    public abstract void start();
    public abstract void stop();
}
