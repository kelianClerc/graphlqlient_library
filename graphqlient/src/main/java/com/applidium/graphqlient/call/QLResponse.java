package com.applidium.graphqlient.call;

import com.applidium.graphqlient.QLResponseModel;

public class QLResponse {
    private String rawResponse;
    private QLResponseModel responses;

    public QLResponse() {
    }

    public QLResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public QLResponse(QLResponseModel responses) {
        this.responses = responses;
    }

    public QLResponse(String rawResponse, QLResponseModel responses) {
        this.rawResponse = rawResponse;
        this.responses = responses;
    }

    public QLResponseModel getResponses() {
        return responses;
    }

    public void setResponses(QLResponseModel responses) {
        this.responses = responses;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

}
