package com.applidium.graphql.client.data.net.graphql.model;

import com.applidium.graphqlient.annotations.Argument;
import com.applidium.graphqlient.annotations.Parameters;
import com.applidium.graphqlient.model.QLModel;

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

    public Endpoint() {
    }
}
