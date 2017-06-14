package com.applidium.graphql.client.data.net;

import com.applidium.graphql.client.core.boundary.GraphQLRepository;
import com.applidium.graphql.client.core.interactor.sendrequest.Response;

import java.io.IOException;

import javax.inject.Inject;

public class ServiceGraphQLRepository implements GraphQLRepository {

    @Inject
    ServiceGraphQLRepository() {
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
        return null;
    }
}
