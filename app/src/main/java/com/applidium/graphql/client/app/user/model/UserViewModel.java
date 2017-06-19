package com.applidium.graphql.client.app.user.model;

import java.util.List;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface UserViewModel {
    String name();
    String email();
    String memberSince();
    List<PostViewModel> posts();
}
