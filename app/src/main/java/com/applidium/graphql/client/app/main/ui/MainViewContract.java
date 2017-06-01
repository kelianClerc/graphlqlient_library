package com.applidium.graphql.client.app.main.ui;

import com.applidium.graphql.client.app.common.ViewContract;

public interface MainViewContract extends ViewContract {
    void showResponse(String responseText);
    void showRequest(String requestText);
    void showVariables(String variableText);
}
