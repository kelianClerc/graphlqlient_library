package com.applidium.graphql.client.data.net.retrofit.model;

import java.util.List;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface RestUsers {
    String id();
    String name();
    String email();
    String createdAt();
    List<RestPosts> posts();
}
