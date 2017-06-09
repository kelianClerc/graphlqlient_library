package com.applidium.graphql.client.app.selection.ui.fragment;

import com.applidium.graphql.client.app.common.ViewContract;
import com.applidium.graphql.client.app.selection.model.ModelViewModel;
import com.applidium.graphqlient.QLQuery;

public interface AddElementViewContract extends ViewContract {
    void showQuery(String query);
    void showContinueButton(QLQuery query);
    void hideContinueButton();
    void displayModel(ModelViewModel model);
    void shouldDisplayBackButton(boolean shouldDisplay);
}
