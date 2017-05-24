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
        graphQL = new GraphQL();
    }

    @Override
    public Response getResponse(String request) throws IOException {

        qlQuery = new QLQuery("test");

        Endpoint endpoints = new Endpoint();
        qlQuery.append(endpoints.getUser());

        String printQuery = qlQuery.printQuery();
        Timber.i(printQuery);
        return new Response(graphQL.sendRequest(printQuery), printQuery);
    }

    @Override
    public Response createResponseFromString(String request) throws IOException {
        QLQuery qlQuery1 = graphQL.createQueryFromString(request);
        Timber.i(qlQuery1.printQuery());
        return new Response(graphQL.sendRequest(qlQuery1.printQuery()), qlQuery1.printQuery());
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
        return new Response(graphQL.sendRequest(printQuery, variables.print()), qlQuery.printQuery());

    }

    private void appendQueryParams() {
        List<QLVariablesElement> queryParams = new ArrayList<>();
        queryParams.add(new QLVariablesElement("userId", QLType.ID, true));
        qlQuery.setParameters(queryParams);
    }

}
