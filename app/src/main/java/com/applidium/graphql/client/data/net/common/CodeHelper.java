package com.applidium.graphql.client.data.net.common;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CodeHelper {

    private static final int UNAUTHORIZED = 401;
    private static final int BAD_REQUEST = 400;
    private static final int INTERNAL_ERROR = 500;
    private static final int UPPER_BOUND = 600;

    @Inject CodeHelper() {}

    public boolean unauthenticated(int code) {
        return code == UNAUTHORIZED;
    }
    public boolean clientError(int code) {
        return code >= BAD_REQUEST && code < INTERNAL_ERROR;
    }
    public boolean serverError(int code) {
        return code >= INTERNAL_ERROR && code < UPPER_BOUND;
    }
}
