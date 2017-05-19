package com.applidium.graphql.client.app.main.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.applidium.graphql.client.R;
import com.applidium.graphql.client.app.common.BaseActivity;
import com.applidium.graphql.client.app.main.presenter.MainPresenter;
import com.applidium.graphql.client.app.main.ui.MainViewContract;
import com.applidium.graphql.client.di.ComponentManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainViewContract {

    @BindView(R.id.launch) Button launch;
    @BindView(R.id.request) TextView request;
    @BindView(R.id.response) TextView response;

    @Inject MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupListeners();
    }

    @Override
    protected void injectDependencies() {
        ComponentManager.getMainComponent(this, this).inject(this);
    }

    private void setupView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void setupListeners() {
        launch.setOnClickListener(getOnLaunchClickListener());
    }

    private View.OnClickListener getOnLaunchClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLaunchRequest(request.getText().toString());
            }
        };
    }

    @Override
    public void showRequest(String requestText) {
        request.setText(requestText);
    }

    @Override
    public void showResponse(String responseText) {
        response.setText(formatJson(responseText));
    }

    private String formatJson(String raw) {
        StringBuilder json = new StringBuilder();
        String indentString = "";

        for (int i = 0; i < raw.length(); i++) {
            char letter = raw.charAt(i);
            switch (letter) {
                case '{':
                case '[':
                    json.append("\n" + indentString + letter + "\n");
                    indentString = indentString + "\t";
                    json.append(indentString);
                    break;
                case '}':
                case ']':
                    indentString = indentString.replaceFirst("\t", "");
                    json.append("\n" + indentString + letter);
                    break;
                case ',':
                    json.append(letter + "\n" + indentString);
                    break;

                default:
                    json.append(letter);
                    break;
            }
        }

        return json.toString();
    }
}
