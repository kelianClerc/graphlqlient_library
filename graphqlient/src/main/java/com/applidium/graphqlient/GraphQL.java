package com.applidium.graphqlient;

import com.applidium.graphqlient.call.QLCall;
import com.applidium.graphqlient.call.QLResponse;
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

    public GraphQL(String baseUrl) {
        this.baseUrl = baseUrl;
        client = new OkHttpClient();
    }

    public QLResponse send(QLRequest request) throws QLException {
        return send(call(request));
    }

    public QLResponse send(QLCall call) throws QLException {
        QLResponse response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new QLException("Connection to server non available : " + e.getMessage());
        }
        return response;
    }

    public QLCall call(QLRequest query) throws QLException {
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

        return new QLCall(query, client.newCall(request));
    }
}
