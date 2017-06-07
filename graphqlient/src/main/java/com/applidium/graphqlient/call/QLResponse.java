package com.applidium.graphqlient.call;

import com.applidium.graphqlient.model.QLModel;

import java.util.List;

public class QLResponse {
    private String rawResponse;
    private List<QLModel> responses;

    public QLResponse() {
    }

    public QLResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public QLResponse(List<QLModel> responses) {
        this.responses = responses;
    }

    public QLResponse(String rawResponse, List<QLModel> responses) {
        this.rawResponse = rawResponse;
        this.responses = responses;
    }

    public List<QLModel> getResponses() {
        return responses;
    }

    public void setResponses(List<QLModel> responses) {
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
