package com.applidium.graphql.client.data.net;

import com.applidium.graphql.client.core.boundary.GraphQLRepository;
import com.applidium.graphql.client.core.interactor.sendrequest.Response;
import com.applidium.graphql.client.data.net.graphql.model.Endpoint;
import com.applidium.graphqlient.GraphQL;
import com.applidium.graphqlient.QLQuery;
import com.applidium.graphqlient.QLType;
import com.applidium.graphqlient.QLVariables;
import com.applidium.graphqlient.QLVariablesElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

public class ServiceGraphQLRepository implements GraphQLRepository {

    private final GraphQL graphQL;
    private QLQuery qlQuery;

    @Inject ServiceGraphQLRepository() {
        graphQL = new GraphQL("http://localhost:3000/graphql/test");
    }

    @Override
    public Response getResponse(String request) throws IOException {

        qlQuery = new QLQuery("test");

        Endpoint endpoints = new Endpoint();
        qlQuery.append(endpoints.getUser());

        String printQuery = qlQuery.printQuery();
        Timber.i(printQuery);
        return new Response(graphQL.send(printQuery), printQuery, "");
    }

    @Override
    public Response createResponseFromString(String request) throws IOException {
        return createResponseFromString(request, "");
    }

    @Override
    public Response createResponseFromString(String request, String variables) throws IOException {
        return new Response(graphQL.send(request, variables), request, variables);
    }

    @Override
    public Response getResponseWithQueryParams(int id) throws IOException {
        qlQuery = new QLQuery("withParams");

        appendQueryParams();

        Endpoint endpoint = new Endpoint();
        qlQuery.append(endpoint.getUserParam());

        Map<String, Object> varMap = new HashMap<>();
        varMap.put("userId", 1);
        QLVariables variables = new QLVariables(varMap);

        String printQuery = qlQuery.printQuery();
        Timber.i(printQuery);
        return new Response(graphQL.send(qlQuery, variables), printQuery, variables.print());

    }

    private void appendQueryParams() {
        List<QLVariablesElement> queryParams = new ArrayList<>();
        queryParams.add(new QLVariablesElement("userId", QLType.ID, true));
        qlQuery.setParameters(queryParams);
    }

}
