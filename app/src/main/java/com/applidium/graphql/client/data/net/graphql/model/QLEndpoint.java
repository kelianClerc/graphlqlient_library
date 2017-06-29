package com.applidium.graphql.client.data.net.graphql.model;

import com.applidium.graphqlient.annotations.Argument;
import com.applidium.graphqlient.annotations.Parameters;
import com.applidium.graphqlient.model.QLModel;

import java.util.List;

public class QLEndpoint implements QLModel {
    @Parameters(table={
        @Argument(argumentName = "id", argumentValue = "1")
    })
    //private User user;
    private List<QLUser> QLUsers;

    @Parameters(table={
        @Argument(argumentName = "id", argumentVariable = "userId")
    })
    private QLUser QLUser;

    public QLEndpoint() {
    }
}
