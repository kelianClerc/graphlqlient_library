package com.applidium.graphql.client.core.interactor.listofusers;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface ListUsersResponse {
    String name();
    String email();
    String id();
    int numberOfPosts();
}
