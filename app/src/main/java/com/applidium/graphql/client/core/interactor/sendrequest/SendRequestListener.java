package com.applidium.graphql.client.core.interactor.sendrequest;

public interface SendRequestListener {
    void onSendRequestResponse(String response);
    void onSendRequestError(String message);
}
