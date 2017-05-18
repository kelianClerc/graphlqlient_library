package com.applidium.graphql.client.core.error.exceptions;

import com.applidium.graphql.client.core.error.GraphQL_AndroidException;
import com.applidium.graphql.client.core.error.Errors;

public class MappingException extends GraphQL_AndroidException {

    public MappingException() {
        /* no-op */
    }

    public MappingException(String message) {
        super(message);
    }

    @Override public int getId() {
        return Errors.MAPPING;
    }
}
