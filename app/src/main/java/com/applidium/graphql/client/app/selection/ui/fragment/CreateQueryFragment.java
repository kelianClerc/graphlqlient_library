package com.applidium.graphql.client.app.selection.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.applidium.graphql.client.R;
import com.applidium.graphql.client.app.common.BaseFragment;
import com.applidium.graphql.client.app.selection.presenter.CreateQueryPresenter;
import com.applidium.graphql.client.app.selection.ui.activity.SelectionActivity;
import com.applidium.graphql.client.di.ComponentManager;
import com.applidium.graphqlient.QLQuery;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class CreateQueryFragment extends BaseFragment implements
    CreateQueryViewContract, AdapterView.OnItemSelectedListener
{

    @BindView(R.id.config_content) FrameLayout content;
    @BindView(R.id.continue_query) Button continueButton;
    @Inject CreateQueryPresenter presenter;

    private LayoutInflater inflater;
    private String selected;
    private SelectionActivity activity;
    private QLQuery query;

    @Override
    protected void injectDependencies() {
        FragmentManager fm = getFragmentManager();
        ComponentManager.getConfigQueryComponent(this, getContext(), fm).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(
        LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.fragment_config_query, container, false);
        ButterKnife.bind(this, view);
        activity = (SelectionActivity) getActivity();
        continueButton.setOnClickListener(getOnContinueListener());
        presenter.start();
        return view;
    }

    private View.OnClickListener getOnContinueListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addElementsToQuery(query);
            }
        };
    }

    @Override
    public void createQuery() {
        populateQueryScreen();
    }

    @Override
    public void showQuery(String query) {
        activity.updateQuery(query);
    }

    @Override
    public void showContinueButton(QLQuery query) {
        this.query = query;
        continueButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContinueButton() {
        this.query = null;
        continueButton.setVisibility(GONE);
    }

    private void populateQueryScreen() {
        content.removeAllViews();
        View view = inflater.inflate(R.layout.partial_query_creator, content, false);
        final Spinner spinner = ButterKnife.findById(view, R.id.param_type);
        Button button = ButterKnife.findById(view, R.id.validate);
        final TextView queryName = ButterKnife.findById(view, R.id.query_name);
        final TextView paramName = ButterKnife.findById(view, R.id.param_name);
        final CheckBox paramMandatory = ButterKnife.findById(view, R.id.param_mandatory);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onQueryCreated(
                    queryName.getText().toString(),
                    paramName.getText().toString(),
                    String.valueOf(spinner.getSelectedItem()),
                    paramMandatory.isChecked()
                );
            }});

        content.addView(view);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       selected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}