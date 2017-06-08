package com.applidium.graphql.client.core.entity;

import com.applidium.graphqlient.annotations.Argument;
import com.applidium.graphqlient.annotations.Parameters;
import com.applidium.graphqlient.model.QLModel;

import java.util.List;

public class Endpoint implements QLModel {
    @Parameters(table={
        @Argument(argumentName = "id", argumentValue = "1")
    })
    //private User user;
    private List<User> QLUsers;

    @Parameters(table={
        @Argument(argumentName = "id", argumentVariable = "userId")
    })
    private User QLUser;

    public Endpoint() {
    }
}
