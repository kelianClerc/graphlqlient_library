package com.applidium.graphql.client.core.boundary;

import com.applidium.graphql.client.core.interactor.sendrequest.Response;

import java.io.IOException;

public interface GraphQLRepository {
    Response getResponse(String request) throws IOException;
}
