package com.applidium.graphql.client.app.main.navigator;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

public class MainNavigator {
    private Context context;

    @Inject MainNavigator(Context context) {
        this.context = context;
    }

    public void navigateToAutoRequest() {
        // TODO (kelianclerc) 1/6/17
        Toast.makeText(context, "Navigate to auto", Toast.LENGTH_SHORT).show();
    }

    public void navigateToTextRequest() {
        // TODO (kelianclerc) 1/6/17
        Toast.makeText(context, "Navigate to text", Toast.LENGTH_SHORT).show();
    }
}
