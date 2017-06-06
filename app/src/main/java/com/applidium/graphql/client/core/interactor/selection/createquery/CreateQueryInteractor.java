package com.applidium.graphql.client.core.interactor.selection.createquery;

import com.applidium.graphql.client.core.boundary.SelectionRepository;
import com.applidium.graphql.client.utils.threading.RunOnExecutionThread;
import com.applidium.graphql.client.utils.threading.RunOnPostExecutionThread;
import com.applidium.graphql.client.utils.trace.Trace;
import com.applidium.graphqlient.QLQuery;
import com.applidium.graphqlient.exceptions.QLException;

import javax.inject.Inject;

public class CreateQueryInteractor {

    private final SelectionRepository repository;

    private CreateQueryRequest request;
    private CreateQueryListener listener;

    @Inject
    CreateQueryInteractor(SelectionRepository repository) {
        this.repository = repository;
    }


    @Trace
    @RunOnExecutionThread
    public void execute(CreateQueryRequest request, CreateQueryListener listener) {
        this.request = request;
        this.listener = listener;
        tryToCreateQuery();
    }

    private void tryToCreateQuery() {
        try {
            createQuery();
        } catch (QLException e) {
            handleError(e.getMessage());
        }
    }

    private void createQuery() throws QLException {
        handleSuccess(repository.createQuery(request.queryName(), request.paramName(), request.paramType(), request.isMandatory()));
    }

    @RunOnPostExecutionThread
    private void handleSuccess(QLQuery query) {
        if (listener != null) {
            listener.onCreateQuerySuccess(query);
        }
    }

    @RunOnPostExecutionThread
    private void handleError(String message) {
        if (listener != null) {
            listener.onCreateQueryError(message);
        }
    }
}
