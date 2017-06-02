package com.applidium.graphqlient.call;

public interface QLCallback {
    void onResponse(QLCall call, QLResponse response);
    void onFailure(QLCall call, Throwable t);
}
