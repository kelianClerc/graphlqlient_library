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
    private String variables;

    @Inject
    SendRequestInteractor(GraphQLRepository repository) {
        this.repository = repository;
    }

    @Trace
    @RunOnExecutionThread
    public void execute(String request, String variables, SendRequestListener listener) {
        this.request = request;
        this.variables = variables;
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
        Response response;
        if (!request.isEmpty()) {
            response = repository.createResponseFromString(request, variables);
        } else {
            response = repository.getResponseWithQueryParams(1);
        }
        //response = repository.createResponseFromString(response.getRequest());
        handleSuccess(response.getResponse(), response.getRequest(), response.getVariables());
    }

    @RunOnPostExecutionThread
    private void handleSuccess(String response, String request, String variables) {
        if (listener != null) {
            listener.onSendRequestResponse(response, request, variables);
        }
    }

    @RunOnPostExecutionThread
    private void handleError(String message) {
        if (listener != null) {
            listener.onSendRequestError(message);
        }
    }
}
