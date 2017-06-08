package com.applidium.graphql.client.app.selection.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applidium.graphql.client.R;
import com.applidium.graphql.client.app.common.BaseFragment;
import com.applidium.graphql.client.app.selection.model.ModelViewModel;
import com.applidium.graphql.client.app.selection.presenter.AddElementPresenter;
import com.applidium.graphql.client.di.ComponentManager;
import com.applidium.graphqlient.QLQuery;

import javax.inject.Inject;

public class AddElementFragment extends BaseFragment implements AddElementViewContract {

    @Inject AddElementPresenter presenter;

    @Override
    protected void injectDependencies() {
        FragmentManager fm = getFragmentManager();
        ComponentManager.getAddElementComponent(this, getContext(), fm).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_element, container, false);
        return view;
    }


    @Override
    public void showQuery(String query) {

    }

    @Override
    public void showContinueButton(QLQuery query) {

    }

    @Override
    public void hideContinueButton() {

    }

    @Override
    public void displayModel(ModelViewModel model) {
        // TODO (kelianclerc) 8/6/17
    }
}
