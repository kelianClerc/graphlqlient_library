package com.applidium.graphql.client.core.error.exceptions;

public abstract class DemoException extends Exception {

    public DemoException() {}

    public DemoException(String message) {
        super(message);
    }

    public abstract int getId();
}

