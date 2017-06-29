package com.applidium.graphql.client.core.entity;

import android.support.annotation.Nullable;

import com.applidium.graphqlient.model.QLModel;

import java.util.List;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface User extends QLModel {
    @Nullable String id();
    @Nullable String name();
    @Nullable String essai();
    @Nullable List<Post> posts();
}
