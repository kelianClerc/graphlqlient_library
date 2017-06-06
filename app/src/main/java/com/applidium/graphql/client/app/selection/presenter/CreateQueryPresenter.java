package com.applidium.graphql.client.app.selection.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.selection.ui.fragment.CreateQueryViewContract;
import com.applidium.graphql.client.core.interactor.selection.createquery.CreateQueryInteractor;
import com.applidium.graphql.client.core.interactor.selection.createquery.CreateQueryListener;
import com.applidium.graphql.client.core.interactor.selection.createquery.CreateQueryRequest;
import com.applidium.graphql.client.core.interactor.selection.createquery.CreateQueryRequestBuilder;
import com.applidium.graphqlient.QLQuery;

import javax.inject.Inject;

public class CreateQueryPresenter extends Presenter<CreateQueryViewContract> implements CreateQueryListener {

    private final CreateQueryInteractor createQueryInteractor;

    @Inject
    CreateQueryPresenter(CreateQueryViewContract view, CreateQueryInteractor createQueryInteractor) {
        super(view);
        this.createQueryInteractor = createQueryInteractor;
    }

    @Override
    public void start() {
        view.createQuery();
    }

    @Override
    public void stop() {

    }

    public void onQueryCreated(
        String queryName, String paramName, String selected, boolean checked
    ) {
        CreateQueryRequest request = new CreateQueryRequestBuilder()
            .queryName(queryName)
            .paramName(paramName == null ? "" : paramName)
            .paramType(selected == null ? "" : selected)
            .isMandatory(checked)
            .build();
        createQueryInteractor.execute(request, this);

    }

    @Override
    public void onCreateQuerySuccess(QLQuery query) {
        view.showQuery(query.printQuery());
        // TODO (kelianclerc) 6/6/17 go to next step
    }

    @Override
    public void onCreateQueryError(String message) {
        view.showQuery(message);
    }
}
