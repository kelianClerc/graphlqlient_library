package com.applidium.graphqlient.call;

import java.util.Map;

public class QLResponse {
    private String rawResponse;
    private Map<String, Object> responses;

    public QLResponse() {
    }

    public QLResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public QLResponse(Map<String, Object> responses) {
        this.responses = responses;
    }

    public QLResponse(String rawResponse, Map<String, Object> responses) {
        this.rawResponse = rawResponse;
        this.responses = responses;
    }

    public Map<String, Object> getResponses() {
        return responses;
    }

    public void setResponses(Map<String, Object> responses) {
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
        if (responses == null || responses.size() == 0) {
            return rawResponse;
        } else {
            String resp = "";
            for(String key : responses.keySet()) {
                resp += responses.get(key).toString();
            }
            return resp;
        }
    }
}
