package com.applidium.graphql.client.app.selection.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.applidium.graphql.client.R;
import com.applidium.graphql.client.app.common.BaseActivity;
import com.applidium.graphql.client.app.selection.presenter.SelectionPresenter;
import com.applidium.graphql.client.app.selection.ui.SelectionViewContract;
import com.applidium.graphql.client.app.selection.ui.fragment.AddElementFragment;
import com.applidium.graphql.client.app.selection.ui.fragment.CreateQueryFragment;
import com.applidium.graphql.client.app.selection.ui.fragment.DetailsFragment;
import com.applidium.graphql.client.di.ComponentManager;
import com.applidium.graphqlient.QLQuery;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectionActivity extends BaseActivity implements
    SelectionViewContract, Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Inject SelectionPresenter presenter;

    private FragmentManager manager;
    private DetailsFragment fragment;
    private Fragment createQueryFragment;

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectionActivity.class);
    }

    @Override
    protected void injectDependencies() {
        manager = getSupportFragmentManager();
        ComponentManager.getSelectionComponent(this, this, manager).inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupMenu();
        setupFragments();
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
                onBackPressed();
            }
        };
    }

    private void setupView() {
        setContentView(R.layout.activity_selection);
        ButterKnife.bind(this);

    }

    private void setupFragments() {
        FragmentTransaction ft = manager.beginTransaction();
        fragment = new DetailsFragment();
        createQueryFragment = new CreateQueryFragment();
        ft.add(R.id.details, fragment);
        ft.add(R.id.config, createQueryFragment);
        ft.commit();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.validate_request:
                presenter.onValidate();
                Toast.makeText(this, "Validate", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    public void updateQuery(String query) {
        fragment.showQuery(query);
    }

    public void addElementsToQuery(QLQuery query) {
        FragmentTransaction ft = manager.beginTransaction();
        createQueryFragment = new AddElementFragment();
        ft.replace(R.id.config, createQueryFragment);
        ft.commit();
    }
}

