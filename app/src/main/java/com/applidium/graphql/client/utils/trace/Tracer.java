package com.applidium.graphql.client.utils.trace;

public interface Tracer {
    void trace(Object target, String message, Object... parameterValues);
}
