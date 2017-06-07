package com.applidium.graphqlient.call;

import java.util.List;

public class QLResponse {
    private String rawResponse;
    private List<?> responses;

    public QLResponse() {
    }

    public QLResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public QLResponse(List<?> responses) {
        this.responses = responses;
    }

    public QLResponse(String rawResponse, List<?> responses) {
        this.rawResponse = rawResponse;
        this.responses = responses;
    }

    public List<?> getResponses() {
        return responses;
    }

    public void setResponses(List<?> responses) {
        this.responses = responses;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    @Override
    public String toString() {
        return rawResponse;
    }
}
