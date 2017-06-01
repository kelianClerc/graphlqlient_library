package com.applidium.graphqlient;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Call;
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

    public String send(String query) throws IOException {
        return send(query, "");
    }

    public String send(String query, @Nullable String variables) throws IOException {
        return send(call(query, variables));
    }

    public String send(String query, QLVariables variables) throws IOException {
        return send(call(query, variables));
    }

    public String send(QLQuery query, QLVariables variables) throws IOException {
        return send(call(query, variables));
    }

    public String send(Call call) throws IOException {
        Response response = call.execute();
        return response.body().string();
    }

    public QLQuery buildQuery(String query) {
        QLParser qlParser = new QLParser(query);
        return qlParser.buildQuery();
    }

    public Call call(String query) {
        return call(query, "");
    }

    public Call call(String query, String variables) {
        return call(buildQuery(query), variables);
    }

    public Call call(String query, QLVariables variables) {
        return call(buildQuery(query), variables);
    }

    public Call call(QLQuery query) {
        return call(query, "");
    }

    public Call call(QLQuery query, QLVariables variables) {
        return call(query, variables.toString());
    }

    public Call call(QLQuery query, String variables) {
        HttpUrl.Builder builder = HttpUrl.parse(BASE_URL).newBuilder();

        builder.addQueryParameter(QUERY_PARAMETER, query.printQuery());
        if (variables != null && !variables.isEmpty()) {
            builder.addQueryParameter(VARIABLE_PARAMETER, variables);
        }
        HttpUrl toCallUrl = builder.build();

        Request request = new Request.Builder()
            .url(toCallUrl)
            .build();

        return client.newCall(request);
    }


}
