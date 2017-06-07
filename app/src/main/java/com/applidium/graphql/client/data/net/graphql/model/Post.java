package com.applidium.graphql.client.data.net.graphql.model;

import android.support.annotation.Nullable;

import com.applidium.graphqlient.model.QLModel;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface Post extends QLModel {
    @Nullable String id();
    @Nullable String title();
}
