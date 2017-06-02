package com.applidium.graphqlient;

import android.support.annotation.Nullable;

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

    public String send(String query) throws IOException, QLException {
        return send(query, "");
    }

    public String send(String query, @Nullable String variables) throws IOException, QLException {
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

    public QLCall call(String query) throws QLException {
        return call(query, "");
    }

    public QLCall call(String query, String variables) throws QLException {
        QLQuery qlQuery = buildQuery(query);
        QLVariables qlVariables = QLParser.parseVariables(variables);
        qlQuery.setVariables(qlVariables);
        return call(qlQuery);
    }

    public QLCall call(String query, QLVariables variables) {
        return call(buildQuery(query), variables);
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

    public QLCall call(QLQuery query) throws QLException {
        HttpUrl.Builder builder = HttpUrl.parse(baseUrl).newBuilder();

        builder.addQueryParameter(QUERY_PARAMETER, query.printQuery());
        if (query.areAllParametersGiven()) {
            if (!query.isVariableEmpty()) {
                builder.addQueryParameter(VARIABLE_PARAMETER, query.getVariables().print());
            }
        } else {
            String message = "Not all mandatory parameters of query " + query.getName() + " are " +
                "provided as QLVariable";
            throw new QLException(message);
        }
        HttpUrl toCallUrl = builder.build();

        Request request = new Request.Builder()
            .url(toCallUrl)
            .build();

        return new QLCall(query, client.newCall(request));
    }


}
