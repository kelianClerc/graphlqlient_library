package com.applidium.graphqlient;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GraphQL {
    private final OkHttpClient client;
    private static String BASE_URL = "http://localhost:3000/graphql/test";
    private static String QUERY_PARAMETER = "query";
    private static String VARIABLE_PARAMETER = "variables";

    public GraphQL() {
        client = new OkHttpClient();
    }

    public String sendRequest(String query) throws IOException {
        return sendRequest(query, null);
    }

    public String sendRequest(String query, @Nullable String variables) throws IOException {

        HttpUrl.Builder builder = HttpUrl.parse(BASE_URL).newBuilder();

        builder.addQueryParameter(QUERY_PARAMETER, query);
        if (variables != null && !variables.isEmpty()) {
            builder.addQueryParameter(VARIABLE_PARAMETER, variables);
        }
        HttpUrl toCallUrl = builder.build();

        Request request = new Request.Builder()
            .url(toCallUrl)
            .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    public QLQuery createQueryFromString(String query) {
        QLParser qlParser = new QLParser(query);
        return qlParser.begin();
    }
}
