package com.applidium.graphql.client.core.interactor.sendrequest;

public class Response {
    private String response;
    private String request;
    private String variables;

    public Response(String response, String request, String variables) {
        this.response = response;
        this.request = request;
        this.variables = variables;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }
}
