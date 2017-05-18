package com.applidium.graphql.client.core.error;

public abstract class GraphQL_AndroidException extends Exception {
    public GraphQL_AndroidException() {
    }

    public GraphQL_AndroidException(String message) {
        super(message);
    }

    public abstract int getId();
}
