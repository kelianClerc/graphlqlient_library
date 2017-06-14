package com.applidium.graphql.client.data.net;

import com.applidium.graphql.client.TestQueryRequest;
import com.applidium.graphql.client.core.boundary.GraphQLRepository;
import com.applidium.graphql.client.core.interactor.sendrequest.Response;
import com.applidium.graphqlient.GraphQL;
import com.applidium.graphqlient.call.QLResponse;
import com.applidium.graphqlient.exceptions.QLException;

import org.json.JSONException;

import java.io.IOException;

import javax.inject.Inject;

public class ServiceGraphQLRepository implements GraphQLRepository {

    private final GraphQL graphQL;

    @Inject
    ServiceGraphQLRepository() {
        graphQL = new GraphQL("http://localhost:3000/graphql/test");
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
        TestQueryRequest request = new TestQueryRequest(String.valueOf(id));

        try {
            QLResponse response = graphQL.send(request);
            return new Response(response.getResponses().toString(), request.query(), request.variables());
        } catch (QLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
