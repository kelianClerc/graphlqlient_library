package com.applidium.graphql.client.core.interactor.sendrequest;

public interface SendRequestListener {
    void onSendRequestResponse(String response, String request);
    void onSendRequestError(String message);
}
