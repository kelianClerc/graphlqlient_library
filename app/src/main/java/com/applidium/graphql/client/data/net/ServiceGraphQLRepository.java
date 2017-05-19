package com.applidium.graphql.client.data.net;

import com.applidium.graphql.client.core.boundary.GraphQLRepository;
import com.applidium.graphqlient.GraphQL;
import com.applidium.graphqlient.QLQuery;
import com.applidium.graphqlient.tree.QLLeaf;
import com.applidium.graphqlient.tree.QLNode;

import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;

import timber.log.Timber;

public class ServiceGraphQLRepository implements GraphQLRepository {

    private final GraphQL graphQL;

    @Inject ServiceGraphQLRepository() {
        graphQL = new GraphQL();
    }

    @Override
    public String getStringResponse(String request) throws IOException {
        QLQuery query = computeTree();
        String printQuery = query.printQuery();
        Timber.i(printQuery);
        return graphQL.sendRequest(printQuery);
    }

    public QLQuery computeTree() {
        QLQuery qlQuery = new QLQuery("test");


        HashMap<String, Object> paramsId = new HashMap<>();
        paramsId.put("id", 1);
        String alias = "Perso";
        QLNode root = new QLNode("user", alias, paramsId);
        HashMap<String, Object> params = new HashMap<>();
        params.put("length", 4);
        root.addChild(new QLLeaf("name", params));

        qlQuery.append(root);

        return qlQuery;
    }
}
