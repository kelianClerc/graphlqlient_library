package com.applidium.graphql.client.core.interactor.userposts;

import com.applidium.graphql.client.core.entity.User;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface UserPostsResponse {
    User userResponse();
}
