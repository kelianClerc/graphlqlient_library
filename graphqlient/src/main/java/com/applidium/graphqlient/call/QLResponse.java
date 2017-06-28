package com.applidium.graphqlient.call;

import com.applidium.graphqlient.QLResponseModel;

import okhttp3.Response;

public class QLResponse<T> {
    private Response rawResponse;
    private T response;

    public QLResponse() {
    }

    public QLResponse(Response rawResponse) {
        this.rawResponse = rawResponse;
    }

    public QLResponse(T response) {
        this.response = response;
    }

    public QLResponse(Response rawResponse, T response) {
        this.rawResponse = rawResponse;
        this.response = response;
    }

    public QLResponse(Response response, QLResponseModel convert) {
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public Response getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(Response rawResponse) {
        this.rawResponse = rawResponse;
    }

    public static <T> QLResponse<T> create(Response response, T convert) {
        return new QLResponse<>(response, convert);
    }
}
