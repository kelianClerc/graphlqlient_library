package com.applidium.graphql.client.core.boundary;

import java.io.IOException;

public interface GraphQLRepository {
    String getStringResponse(String request) throws IOException;
}
