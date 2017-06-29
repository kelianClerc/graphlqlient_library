package com.applidium.graphql.client.data.net;

import com.applidium.graphql.client.DynamicParameterQueryRequest;
import com.applidium.graphql.client.DynamicParameterQueryResponse;
import com.applidium.graphql.client.core.boundary.GraphQLRepository;
import com.applidium.graphql.client.core.interactor.sendrequest.Response;
import com.applidium.graphqlient.GraphQL;
import com.applidium.graphqlient.call.QLResponse;
import com.applidium.graphqlient.converter.gson.GsonConverterFactory;
import com.applidium.graphqlient.exceptions.QLException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import javax.inject.Inject;

import io.norberg.automatter.gson.AutoMatterTypeAdapterFactory;

public class ServiceGraphQLRepository implements GraphQLRepository {

    private final GraphQL graphQL;

    @Inject
    ServiceGraphQLRepository() {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new AutoMatterTypeAdapterFactory()).create();
        GsonConverterFactory converterFactory = GsonConverterFactory.create(gson);
        this.graphQL = new GraphQL("http://localhost:3000/graphql/test", converterFactory);
    }

    @Override
    public Response getResponse(String request) throws IOException {
        return null;
    }

    @Override
    public Response createResponseFromString(String request) throws IOException {
        return null;
    }

    @Override
    public Response createResponseFromString(String request, String variables) throws IOException {
        return null;
    }

    @Override
    public Response getResponseWithQueryParams(int id) throws IOException {
        DynamicParameterQueryRequest request = new DynamicParameterQueryRequest(String.valueOf(id));

        try {
            QLResponse response = graphQL.send(request);
            DynamicParameterQueryResponse resp = (DynamicParameterQueryResponse) response.getResponses();
            return new Response(response.getResponses().toString(), request.query(), request.variables());
        } catch (QLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
