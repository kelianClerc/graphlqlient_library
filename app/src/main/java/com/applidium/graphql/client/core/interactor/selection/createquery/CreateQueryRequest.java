package com.applidium.graphql.client.core.interactor.selection.createquery;

import android.support.annotation.Nullable;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface CreateQueryRequest {
    String queryName();
    @Nullable String paramName();
    boolean isMandatory();
    @Nullable String paramType();
}
