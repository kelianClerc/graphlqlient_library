package com.applidium.graphqlient.call;

import com.applidium.graphqlient.QLResponseModel;
import com.applidium.graphqlient.errorhandling.QLErrorsResponse;

import okhttp3.Response;

public class QLResponse<T> {
    private Response rawResponse;
    private T response;
    private boolean isErrorResponse;
    private QLErrorsResponse errorsResponse;

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
        isErrorResponse = false;
    }

    public QLResponse(Response rawResponse, boolean isErrorResponse, QLErrorsResponse
        errorsResponse) {
        this.rawResponse = rawResponse;
        this.isErrorResponse = isErrorResponse;
        this.errorsResponse = errorsResponse;
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

    public boolean isErrorResponse() {
        return isErrorResponse;
    }

    public void setErrorResponse(boolean errorResponse) {
        isErrorResponse = errorResponse;
    }

    public QLErrorsResponse getErrorsResponse() {
        return errorsResponse;
    }

    public void setErrorsResponse(QLErrorsResponse errorsResponse) {
        this.errorsResponse = errorsResponse;
    }
}
