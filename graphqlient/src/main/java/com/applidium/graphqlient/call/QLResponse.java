package com.applidium.graphqlient.call;

import com.applidium.graphqlient.QLResponseModel;

import okhttp3.Response;

public class QLResponse<T> {
    private Response rawResponse;
    private T responses;

    public QLResponse() {
    }

    public QLResponse(Response rawResponse) {
        this.rawResponse = rawResponse;
    }

    public QLResponse(T responses) {
        this.responses = responses;
    }

    public QLResponse(Response rawResponse, T responses) {
        this.rawResponse = rawResponse;
        this.responses = responses;
    }

    public QLResponse(Response response, QLResponseModel convert) {
    }

    public T getResponses() {
        return responses;
    }

    public void setResponses(T responses) {
        this.responses = responses;
    }

    public Response getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(Response rawResponse) {
        this.rawResponse = rawResponse;
    }

}
