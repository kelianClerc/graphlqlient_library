package com.applidium.graphql.client.app.selection.ui.fragment;

import com.applidium.graphql.client.app.common.ViewContract;

public interface CreateQueryViewContract extends ViewContract {
    void createQuery();
    void showQuery(String query);
}
