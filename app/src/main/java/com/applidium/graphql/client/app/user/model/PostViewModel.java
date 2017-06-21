package com.applidium.graphql.client.app.user.model;

import android.support.annotation.Nullable;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface PostViewModel {
    String id();
    @Nullable String body();
    @Nullable String creationDate();
    int numberOfComments();
    int votesCount();
    @Nullable String slug();
    @Nullable String title();
    @Nullable String author();

}
