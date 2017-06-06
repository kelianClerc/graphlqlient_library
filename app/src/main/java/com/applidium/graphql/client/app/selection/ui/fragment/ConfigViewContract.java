package com.applidium.graphql.client.app.selection.ui.fragment;

import com.applidium.graphql.client.app.common.ViewContract;
import com.applidium.graphql.client.app.selection.model.ModelViewModel;

public interface ConfigViewContract extends ViewContract {
    void createQuery();
    void showModel(ModelViewModel model);
}
