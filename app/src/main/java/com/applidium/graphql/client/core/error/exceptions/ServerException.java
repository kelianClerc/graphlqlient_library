package com.applidium.graphql.client.core.error.exceptions;

import com.applidium.graphql.client.core.error.GraphQL_AndroidException;
import com.applidium.graphql.client.core.error.Errors;

public class ServerException extends GraphQL_AndroidException {
    @Override public int getId() {
        return Errors.UNAVAILABLE_SERVICE;
    }
}
