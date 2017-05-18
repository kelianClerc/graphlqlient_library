package com.applidium.graphql.client.data.net.common;

import com.applidium.graphql.client.data.net.retrofit.model.RestError;

public interface ServiceErrorHandler {
    void handleClientError(int code, RestError error);
}
