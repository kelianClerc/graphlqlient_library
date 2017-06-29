package com.applidium.graphql.client.app.main.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.applidium.graphql.client.R;
import com.applidium.graphql.client.app.common.BaseActivity;
import com.applidium.graphql.client.app.main.presenter.MainPresenter;
import com.applidium.graphql.client.app.main.ui.MainViewContract;
import com.applidium.graphql.client.di.ComponentManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainViewContract, Toolbar
    .OnMenuItemClickListener {

    @BindView(R.id.reset_request)
    ImageButton reset;
    @BindView(R.id.request) TextView request;
    @BindView(R.id.response) TextView response;
    @BindView(R.id.variable) TextView variable;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Inject MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupListeners();
    }

    private void setupListeners() {
        reset.setOnClickListener(getOnResetClickedListener());
    }

    private View.OnClickListener getOnResetClickedListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setText("");
                response.setText("");
            }
        };
    }

    @Override
    protected void injectDependencies() {
        ComponentManager.getMainComponent(this, this).inject(this);
    }

    private void setupView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.inflateMenu(R.menu.navig);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public void showRequest(String requestText) {
        request.setText(requestText);
    }

    @Override
    public void showVariables(String variableText) {
        variable.setText(variableText);
    }

    @Override
    public void showResponse(String responseText) {
        response.setText(responseText);
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.auto_request:
                presenter.onLaunchRequest("", "");
                return true;
            case R.id.text_request:
                presenter.onLaunchRequest(request.getText().toString(), variable.getText().toString());
                return true;
            case R.id.setting_request:
                presenter.onSettings();
        }
        return false;
    }
}
