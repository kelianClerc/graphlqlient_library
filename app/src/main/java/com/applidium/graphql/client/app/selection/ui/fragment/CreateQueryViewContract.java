package com.applidium.graphql.client.app.selection.ui.fragment;

import com.applidium.graphql.client.app.common.ViewContract;
import com.applidium.graphqlient.QLQuery;

public interface CreateQueryViewContract extends ViewContract {
    void createQuery();
    void showQuery(String query);
    void showContinueButton(QLQuery query);
    void hideContinueButton();
}
