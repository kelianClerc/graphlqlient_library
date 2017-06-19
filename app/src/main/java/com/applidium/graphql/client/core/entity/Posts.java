package com.applidium.graphql.client.core.entity;

import android.support.annotation.Nullable;

import org.joda.time.DateTime;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface Posts {
    @Nullable
    String id();
    @Nullable String body();
    @Nullable String title();
    @Nullable String slug();
    @Nullable String authorName();
    int votesCount();
    int commentsCount();
    @Nullable
    DateTime createdAt();
}
