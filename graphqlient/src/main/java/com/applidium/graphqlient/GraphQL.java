package com.applidium.graphqlient;

import android.support.annotation.Nullable;

import com.applidium.graphqlient.call.QLCall;
import com.applidium.graphqlient.call.QLResponse;

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

    public String send(QLCall call) throws IOException {
        QLResponse response = call.execute();
        return response.toString();
    }

    public QLQuery buildQuery(String query) {
        QLParser qlParser = new QLParser(query);
        return qlParser.buildQuery();
    }

    public QLCall call(String query) {
        return call(query, "");
    }

    public QLCall call(String query, String variables) {
        return call(buildQuery(query), variables);
    }

    public QLCall call(String query, QLVariables variables) {
        return call(buildQuery(query), variables);
    }

    public QLCall call(QLQuery query) {
        return call(query, "");
    }

    public QLCall call(QLQuery query, String variables) {
        return call(query, QLParser.parseVariables(variables));
    }

    public QLCall call(QLQuery query, QLVariables variables) {
        HttpUrl.Builder builder = HttpUrl.parse(baseUrl).newBuilder();

        builder.addQueryParameter(QUERY_PARAMETER, query.printQuery());
        String variablesString = variables.print();
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
