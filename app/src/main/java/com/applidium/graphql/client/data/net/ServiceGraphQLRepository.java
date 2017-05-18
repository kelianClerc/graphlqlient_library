package com.applidium.graphql.client.data.net;

import com.applidium.graphql.client.core.boundary.GraphQLRepository;
import com.applidium.graphqlient.GraphQL;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.http.HEAD;

public class ServiceGraphQLRepository implements GraphQLRepository {

    private final GraphQL graphQL;

    @Inject ServiceGraphQLRepository() {
        graphQL = new GraphQL();
    }

    @Override
    public String getStringResponse(String request) throws IOException {
        return graphQL.sendRequest(request);
    }
}
