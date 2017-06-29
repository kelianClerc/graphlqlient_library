package com.applidium.graphql.client.data.net;

import com.applidium.graphql.client.core.boundary.GraphQLRepository;
import com.applidium.graphql.client.core.interactor.sendrequest.Response;
import com.applidium.graphql.client.data.net.graphql.model.QLEndpoint;
import com.applidium.graphqlient.GraphQL;
import com.applidium.graphqlient.QLQuery;
import com.applidium.graphqlient.QLType;
import com.applidium.graphqlient.QLVariables;
import com.applidium.graphqlient.QLVariablesElement;
import com.applidium.graphqlient.exceptions.QLException;

import org.json.JSONException;

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
        graphQL = new GraphQL("http://localhost:3000/graphql/test", QLEndpoint.class);
    }

    @Override
    public Response getResponse(String request) throws IOException {

        qlQuery = new QLQuery("test");

        QLEndpoint endpoints = new QLEndpoint();
        qlQuery.append(graphQL.endpoints().get("users"));

        String printQuery = qlQuery.printQuery();
        Timber.i(printQuery);
        try {
            return new Response(graphQL.send(printQuery).toString(), printQuery, "");
        } catch (QLException e) {
            e.printStackTrace();
            return new Response(e.getMessage(), printQuery, "");
        } catch (JSONException e1) {
            e1.printStackTrace();
            return new Response(e1.getMessage(), printQuery, "");
        }

    }

    @Override
    public Response createResponseFromString(String request) throws IOException {
        return createResponseFromString(request, "");
    }

    @Override
    public Response createResponseFromString(String request, String variables) throws IOException {
        try {
            return new Response(graphQL.send(request, variables).toString(), request, variables);
        } catch (QLException e) {
            e.printStackTrace();
            return new Response(e.getMessage(), request, "");
        } catch (JSONException e1) {
            e1.printStackTrace();
            return new Response(e1.getMessage(), request, "");
        }
    }

    @Override
    public Response getResponseWithQueryParams(int id) throws IOException {
        qlQuery = new QLQuery("withParams");

        appendQueryParams();

        QLEndpoint QLEndpoint = new QLEndpoint();
        qlQuery.append(graphQL.endpoints().get("user"));

        Map<String, Object> varMap = new HashMap<>();
        varMap.put("userId", 1);
        QLVariables variables = new QLVariables(varMap);

        String printQuery = qlQuery.printQuery();
        Timber.i(printQuery);
        try {
            return new Response(graphQL.send(qlQuery, variables).toString(), printQuery, variables.print());
        } catch (JSONException e) {
            e.printStackTrace();
            return new Response(e.getMessage(), qlQuery.printQuery(), "");
        }

    }

    private void appendQueryParams() {
        List<QLVariablesElement> queryParams = new ArrayList<>();
        queryParams.add(new QLVariablesElement("userId", QLType.ID, true));
        qlQuery.setParameters(queryParams);
    }

}
