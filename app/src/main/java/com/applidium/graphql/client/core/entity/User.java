package com.applidium.graphql.client.core.entity;

import android.support.annotation.Nullable;

import org.joda.time.DateTime;

import java.util.List;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface User {
    @Nullable String name();
    @Nullable String email();
    @Nullable String id();
    @Nullable DateTime createdAt();
    int numberOfPosts();
    @Nullable List<Posts> posts();
}
