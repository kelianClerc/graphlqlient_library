package com.applidium.graphql.client.app.selection.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applidium.graphql.client.R;
import com.applidium.graphql.client.app.common.BaseFragment;
import com.applidium.graphql.client.di.ComponentManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends BaseFragment implements DetailsViewContract {

    @BindView(R.id.query_text) TextView queryText;

    @Override
    protected void injectDependencies() {
        ComponentManager.getLoggingComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(
        LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_detail_query, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void showQuery(String query) {
        queryText.setText(query);
    }
}
