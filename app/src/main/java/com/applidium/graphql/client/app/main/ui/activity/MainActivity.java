package com.applidium.graphql.client.app.main.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.applidium.graphql.client.R;
import com.applidium.graphql.client.app.common.BaseActivity;
import com.applidium.graphql.client.app.main.ui.MainViewContract;
import com.applidium.graphql.client.di.ComponentManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainViewContract {

    @BindView(R.id.launch) Button launch;
    @BindView(R.id.request) TextView request;
    @BindView(R.id.response) TextView response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupListeners();
    }

    @Override
    protected void injectDependencies() {
        ComponentManager.getLoggingComponent().inject(this);
    }

    private void setupView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showRequest("{\n" +
            "\tusers {\n" +
            "    \t\tname\n" +
            "\t}\n" +
            "}");
    }

    private void setupListeners() {
        launch.setOnClickListener(getOnLaunchClickListener());
    }

    private View.OnClickListener getOnLaunchClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //presenter.onLaunchRequest();
            }
        };
    }

    @Override
    public void showRequest(String requestText) {
        request.setText(requestText);
    }

    @Override
    public void showResponse(String responseText) {

    }
}
