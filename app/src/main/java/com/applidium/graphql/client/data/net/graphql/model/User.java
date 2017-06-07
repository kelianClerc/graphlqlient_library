package com.applidium.graphql.client.data.net.graphql.model;

import android.support.annotation.Nullable;

import com.applidium.graphqlient.annotations.Alias;
import com.applidium.graphqlient.model.QLModel;

import java.util.List;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface User extends QLModel {
    @Nullable String id();
    @Nullable String name();
    @Nullable @Alias(name = "essai") String email();
    @Nullable List<Post> posts();
}
