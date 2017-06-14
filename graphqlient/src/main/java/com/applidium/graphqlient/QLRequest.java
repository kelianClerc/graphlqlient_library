package com.applidium.graphqlient;

public interface QLRequest {
    public java.lang.String query() throws com.applidium.graphqlient.exceptions.QLException;
    public String variables();
    public Class<? extends QLResponseModel> target();
}
