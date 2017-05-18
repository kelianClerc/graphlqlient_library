package com.applidium.graphql.client.core.interactor.sendrequest;

import com.applidium.graphql.client.core.boundary.GraphQLRepository;
import com.applidium.graphql.client.utils.threading.RunOnExecutionThread;
import com.applidium.graphql.client.utils.threading.RunOnPostExecutionThread;
import com.applidium.graphql.client.utils.trace.Trace;

import java.io.IOException;

import javax.inject.Inject;

public class SendRequestInteractor {
    private final GraphQLRepository repository;
    private SendRequestListener listener;
    private String request;

    @Inject
    SendRequestInteractor(GraphQLRepository repository) {
        this.repository = repository;
    }

    @Trace
    @RunOnExecutionThread
    public void execute(String request, SendRequestListener listener) {
        this.request = request;
        this.listener = listener;
        tryToSendRequest();
    }

    private void tryToSendRequest() {
        try {
            sendRequest();
        } catch (IOException e) {
            handleError(e.getMessage());
        }
    }

    private void sendRequest() throws IOException {
        String response = repository.getStringResponse(request);
        handleSuccess(response);
    }

    @RunOnPostExecutionThread
    private void handleSuccess(String response) {
        if (listener != null) {
            listener.onSendRequestResponse(response);
        }
    }

    @RunOnPostExecutionThread
    private void handleError(String message) {
        if (listener != null) {
            listener.onSendRequestError(message);
        }
    }
}
