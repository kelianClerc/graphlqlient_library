package com.applidium.graphql.client.app.selection.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.applidium.graphql.client.R;
import com.applidium.graphql.client.app.common.BaseActivity;
import com.applidium.graphql.client.app.selection.ui.SelectionViewContract;
import com.applidium.graphql.client.di.ComponentManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectionActivity extends BaseActivity implements
    SelectionViewContract, Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar) Toolbar toolbar;

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectionActivity.class);
    }

    @Override
    protected void injectDependencies() {
        ComponentManager.getLoggingComponent().inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupMenu();
    }

    private void setupMenu() {
        toolbar.inflateMenu(R.menu.validate);
        toolbar.setNavigationOnClickListener(getOnNavigationClickedListener());
        toolbar.setOnMenuItemClickListener(this);
    }

    private View.OnClickListener getOnNavigationClickedListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO (kelianclerc) 2/6/17
                Toast.makeText(SelectionActivity.this, "Back pressed", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void setupView() {
        setContentView(R.layout.activity_selection);
        ButterKnife.bind(this);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.validate_request:
                // TODO (kelianclerc) 2/6/17
                Toast.makeText(this, "Validate", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}

