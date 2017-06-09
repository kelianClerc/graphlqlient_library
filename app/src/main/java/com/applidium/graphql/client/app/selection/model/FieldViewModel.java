package com.applidium.graphql.client.app.selection.model;

import java.lang.reflect.Type;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface FieldViewModel {
    Type type();
    String fieldName();
    boolean isChecked();
}
