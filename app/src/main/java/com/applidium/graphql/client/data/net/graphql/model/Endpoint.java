package com.applidium.graphql.client.data.net.graphql.model;

import com.applidium.graphqlient.annotations.Argument;
import com.applidium.graphqlient.annotations.Parameters;
import com.applidium.graphqlient.model.QLModel;
import com.applidium.graphqlient.tree.QLNode;

import java.util.List;

public class Endpoint extends QLModel {
    @Parameters(table={
        @Argument(argumentName = "id", argumentValue = "1")
    })
    private User user;
    private List<User> users;

    public Endpoint() {
    }

    public QLNode getUser() {
        try {
            return createNodeFromField(getClass().getDeclaredField("user"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public QLNode getUsers() {
        try {
            return createNodeFromField(getClass().getDeclaredField("users"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
