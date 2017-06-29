package com.applidium.graphql.client.data.net.graphql.model;

import com.applidium.graphqlient.annotations.Argument;
import com.applidium.graphqlient.annotations.Parameters;
import com.applidium.graphqlient.model.QLModel;
import com.applidium.graphqlient.tree.QLNode;
import com.applidium.graphqlient.tree.QLTreeBuilder;

import java.util.List;

public class Endpoint implements QLModel {
    @Parameters(table={
        @Argument(argumentName = "id", argumentValue = "1")
    })
    //private User user;
    private List<User> users;

    @Parameters(table={
        @Argument(argumentName = "id", argumentVariable = "userId")
    })
    private User user;
    private QLTreeBuilder treeBuilder = new QLTreeBuilder();

    public Endpoint() {
    }

    public QLNode getUser() {
        try {
            return treeBuilder.createNodeFromField(getClass().getDeclaredField("user"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public QLNode getUsers() {
        try {
            return treeBuilder.createNodeFromField(getClass().getDeclaredField("users"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public QLNode getUserParam() {
        try {
            return treeBuilder.createNodeFromField(getClass().getDeclaredField("user"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
