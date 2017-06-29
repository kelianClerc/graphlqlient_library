package com.applidium.graphql.client.data.net.retrofit.model;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface RestPosts {
    String id();
    String body();
}
