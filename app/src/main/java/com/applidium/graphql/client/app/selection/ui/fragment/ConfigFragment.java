package com.applidium.graphql.client.app.selection.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.applidium.graphql.client.R;
import com.applidium.graphql.client.app.common.BaseFragment;
import com.applidium.graphql.client.app.selection.model.ModelViewModel;
import com.applidium.graphql.client.app.selection.presenter.ConfigPresenter;
import com.applidium.graphql.client.app.selection.ui.activity.SelectionActivity;
import com.applidium.graphql.client.di.ComponentManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfigFragment extends BaseFragment implements ConfigViewContract {

    @BindView(R.id.do_action) Button button;
    @Inject ConfigPresenter presenter;


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
        View view = inflater.inflate(R.layout.fragment_config_query, container, false);
        ButterKnife.bind(this, view);
        button.setOnClickListener(getOnButtonClickedListener());
        presenter.start();
        return view;
    }

    private View.OnClickListener getOnButtonClickedListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectionActivity selectionActivity = (SelectionActivity) getActivity();
                selectionActivity.onConfigClicked();
            }
        };
    }

    @Override
    public void createQuery() {
        Toast.makeText(getContext(), "Query creation", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showModel(ModelViewModel model) {

    }
}
