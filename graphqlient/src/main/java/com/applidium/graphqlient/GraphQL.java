package com.applidium.graphqlient;

import com.applidium.graphqlient.call.QLCall;
import com.applidium.graphqlient.call.QLResponse;
import com.applidium.graphqlient.converters.Converter;
import com.applidium.graphqlient.exceptions.QLException;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GraphQL {
    private final OkHttpClient client;
    private String baseUrl = "http://localhost:3000/graphql/test";
    private final static String QUERY_PARAMETER = "query";
    private final static String VARIABLE_PARAMETER = "variables";
    private final Converter.Factory converterFactory;

    public GraphQL(String baseUrl, Converter.Factory converterFactory) {
        this.baseUrl = baseUrl;
        this.converterFactory = converterFactory;
        client = new OkHttpClient();
    }

    public <T> QLResponse<T> send(QLRequest request) throws QLException {
        QLCall<T> call = call(request);
        QLResponse<T> response = send(call);
        if (response != null && response.isErrorResponse()) {
            throw new QLException(response.getErrorsResponse().toString());
        }
        return send(call);
    }

    public <T> QLResponse<T> send(QLCall<T> call) throws QLException {
        QLResponse<T> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new QLException("Connection to server non available : " + e.getMessage());
        }
        if (response != null && response.isErrorResponse()) {
            throw new QLException(response.getErrorsResponse().toString());
        }
        return response;
    }

    public <T> QLCall<T> call(QLRequest query) throws QLException {
        HttpUrl.Builder builder = HttpUrl.parse(baseUrl).newBuilder();

        builder.addQueryParameter(QUERY_PARAMETER, query.query());
        String variablesString = query.variables();
        if (variablesString != null && !variablesString.isEmpty()) {
            builder.addQueryParameter(VARIABLE_PARAMETER, variablesString);
        }
        HttpUrl toCallUrl = builder.build();

        Request request = new Request.Builder()
            .url(toCallUrl)
            .build();

        Converter<T> responseBodyConverter = (Converter<T>)(converterFactory.responseBodyConverter
            (query.target()));
        QLCall<T> call = new QLCall<>(query, client.newCall(request), responseBodyConverter);

        return call;
    }

    public static final class Builder {
        private String baseUrl;
        private Converter.Factory converterFactory;

        public Builder() {}

        Builder(GraphQL graphQL) {
            this.converterFactory = graphQL.converterFactory;
            this.baseUrl = graphQL.baseUrl;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder converterFactory(Converter.Factory converterFactory) {
            this.converterFactory = converterFactory;
            return this;
        }

        public GraphQL build() {
            if (baseUrl == null) {
                throw new IllegalStateException("Base URL required");
            }
            if (converterFactory == null) {
                throw new IllegalStateException("GraphQL library needs a converter factory");
            }

            return new GraphQL(this.baseUrl, this.converterFactory);
        }
    }
}
