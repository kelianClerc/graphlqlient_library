package com.applidium.graphql.client.app.users.model;

import android.support.annotation.Nullable;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface UserViewModel {
    @Nullable String name();
    @Nullable String id();
    @Nullable String email();
    int numberOfPosts();
}
