package com.applidium.graphqlient.call;

public interface QLCallback<T> {
    void onResponse(QLCall<T> call, QLResponse response);
    void onFailure(QLCall<T> call, Throwable t);
}
