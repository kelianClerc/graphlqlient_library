package com.applidium.graphql.client.app.main.ui;

import com.applidium.graphql.client.app.common.ViewContract;

public interface MainViewContract extends ViewContract {
    public void showResponse(String responseText);
    public void showRequest(String requestText);
}
