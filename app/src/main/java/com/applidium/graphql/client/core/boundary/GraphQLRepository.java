package com.applidium.graphql.client.core.boundary;

import com.applidium.graphql.client.core.interactor.sendrequest.Response;

import java.io.IOException;

public interface GraphQLRepository {
    Response getResponse(String request) throws IOException;
    Response createResponseFromString(String request) throws IOException;
    Response createResponseFromString(String request, String variables) throws IOException;
    Response getResponseWithQueryParams(int id) throws IOException;
}
