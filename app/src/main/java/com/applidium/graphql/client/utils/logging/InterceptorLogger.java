package com.applidium.graphql.client.utils.logging;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.logging.HttpLoggingInterceptor;

@Singleton
public class InterceptorLogger implements HttpLoggingInterceptor.Logger {

    private final Logger logger;

    @Inject InterceptorLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void log(String message) {
        logger.v(this, message);
    }
}
