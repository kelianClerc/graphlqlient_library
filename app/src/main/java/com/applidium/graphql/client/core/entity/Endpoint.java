package com.applidium.graphql.client.core.entity;

import com.applidium.graphqlient.model.QLModel;

import java.util.List;

public class Endpoint implements QLModel {
    //private User user;
    private List<UserQL> users;

    private UserQL user;

    private PostQL postQL;

    private List<PostQL> postQLs;

    public Endpoint() {
    }
}
