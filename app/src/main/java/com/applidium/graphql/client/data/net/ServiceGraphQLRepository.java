package com.applidium.graphql.client.data.net;

import com.applidium.graphql.client.core.boundary.GraphQLRepository;
import java.io.IOException;

import javax.inject.Inject;

public class ServiceGraphQLRepository implements GraphQLRepository {

    @Inject ServiceGraphQLRepository() {
    }

    @Override
    public String getStringResponse(String request) throws IOException {
        return "";
    }
}
