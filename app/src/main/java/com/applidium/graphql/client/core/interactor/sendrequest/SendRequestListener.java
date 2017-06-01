package com.applidium.graphql.client.core.interactor.sendrequest;

public interface SendRequestListener {
    void onSendRequestResponse(String response, String request, String variables);
    void onSendRequestError(String message);
}
