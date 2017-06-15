package com.applidium.graphql.client.core.entity;

import android.support.annotation.Nullable;

import org.joda.time.DateTime;

import java.util.List;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface User {
    String name();
    String email();
    String id();
    DateTime createdAt();
    int numberOfPosts();
    @Nullable List<Comments> comments();
}
