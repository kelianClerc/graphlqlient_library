package com.applidium.graphql.client.core.entity;

import android.support.annotation.Nullable;

import com.applidium.graphqlient.model.QLModel;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface PostQL extends QLModel {
    @Nullable String id();
    @Nullable String title();
}
