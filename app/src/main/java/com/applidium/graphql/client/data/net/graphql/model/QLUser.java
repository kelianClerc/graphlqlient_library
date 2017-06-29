package com.applidium.graphql.client.data.net.graphql.model;

import android.support.annotation.Nullable;

import com.applidium.graphqlient.annotations.AliasFor;
import com.applidium.graphqlient.model.QLModel;

import java.util.List;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface QLUser extends QLModel {
    @Nullable String id();
    @Nullable String name();
    @Nullable @AliasFor(name = "email") String essai();
    @Nullable List<QLPost> posts();
}
