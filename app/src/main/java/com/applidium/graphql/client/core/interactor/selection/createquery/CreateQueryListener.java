package com.applidium.graphql.client.core.interactor.selection.createquery;

import com.applidium.graphqlient.QLQuery;

public interface CreateQueryListener {
    void onCreateQuerySuccess(QLQuery query);
    void onCreateQueryError(String message);
}
