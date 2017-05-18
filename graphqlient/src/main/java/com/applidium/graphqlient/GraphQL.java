package com.applidium.graphqlient;

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

        HttpUrl toCallUrl = HttpUrl.parse(BASE_URL)
            .newBuilder()
            .addQueryParameter(QUERY_PARAMETER, query)
            .build();

        Request request = new Request.Builder()
            .url(toCallUrl)
            .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }
}
