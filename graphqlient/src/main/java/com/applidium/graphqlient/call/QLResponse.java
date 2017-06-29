package com.applidium.graphqlient.call;

public class QLResponse {
    private String rawResponse;

    public QLResponse(String rawResponse) {
        this.rawResponse = rawResponse;
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
