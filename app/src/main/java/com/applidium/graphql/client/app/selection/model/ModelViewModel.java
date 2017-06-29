package com.applidium.graphql.client.app.selection.model;

import java.util.List;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface ModelViewModel {
    String objectName();
    List<FieldViewModel> fields();
}
