package com.applidium.graphql.client.app.selection.ui.fragment;

import com.applidium.graphqlient.QLQuery;

public interface AddElementViewContract {
    void showQuery(String query);
    void showContinueButton(QLQuery query);
    void hideContinueButton();
}
