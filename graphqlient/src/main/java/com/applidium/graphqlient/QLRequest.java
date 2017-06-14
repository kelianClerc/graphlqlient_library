package com.applidium.graphqlient;

public interface QLRequest {
    public java.lang.String query();
    public String variables();
    public Class<QLResponseModel> target();
}
