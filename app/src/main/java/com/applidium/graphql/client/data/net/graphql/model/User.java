package com.applidium.graphql.client.data.net.graphql.model;

import com.applidium.graphqlient.annotations.Alias;
import com.applidium.graphqlient.model.QLModel;

public class User implements QLModel {
    private String id;
    private String name;
    @Alias(name = "essai") private String email;
    private Post posts;
}
