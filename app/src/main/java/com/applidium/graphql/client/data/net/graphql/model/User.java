package com.applidium.graphql.client.data.net.graphql.model;

import android.support.annotation.Nullable;

import com.applidium.graphqlient.annotations.Alias;
import com.applidium.graphqlient.model.QLModel;

public class User implements QLModel {
    @Nullable private String id;
    @Nullable private String name;
    @Nullable @Alias(name = "essai") private String email;
    @Nullable private Post posts;
}
