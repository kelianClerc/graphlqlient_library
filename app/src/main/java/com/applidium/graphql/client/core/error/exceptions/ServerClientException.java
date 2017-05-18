package com.applidium.graphql.client.core.error.exceptions;

import android.support.annotation.Nullable;

import com.applidium.graphql.client.core.error.Errors;
import com.applidium.graphql.client.core.error.GraphQL_AndroidException;

public class ServerClientException extends GraphQL_AndroidException {

    private final String message;

    public ServerClientException(@Nullable String message) {
        this.message = message;
    }

    public String getServerMessage() {
        return message;
    }

    @Override public int getId() {
        return Errors.SERVER_CLIENT;
    }
}
