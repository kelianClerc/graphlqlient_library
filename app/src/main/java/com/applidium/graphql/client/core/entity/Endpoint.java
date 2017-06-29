package com.applidium.graphql.client.core.entity;

import com.applidium.graphqlient.model.QLModel;

import java.util.List;

public class Endpoint implements QLModel {
    //private User user;
    private List<User> users;

    private User user;

    private Post post;

    private List<Post> posts;

    public Endpoint() {
    }
}
