package com.applidium.graphql.client.app.selection.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.applidium.graphql.client.R;
import com.applidium.graphql.client.app.common.BaseFragment;
import com.applidium.graphql.client.app.selection.model.FieldViewModel;
import com.applidium.graphql.client.app.selection.model.ModelViewModel;
import com.applidium.graphql.client.app.selection.presenter.AddElementPresenter;
import com.applidium.graphql.client.app.selection.ui.activity.SelectionActivity;
import com.applidium.graphql.client.app.selection.ui.adapter.FieldAdapter;
import com.applidium.graphql.client.di.ComponentManager;
import com.applidium.graphqlient.QLQuery;
import com.applidium.graphqlient.QLVariablesElement;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static com.applidium.graphql.client.app.selection.ui.adapter.FieldViewHolder.trimPath;

public class AddElementFragment extends BaseFragment implements AddElementViewContract, FieldAdapter.FieldCheckedListener {


    private static final String QUERY_NAME_KEY = "QUERY_NAME_KEY";
    private static final String QUERY_PARAM_KEY = "QUERY_PARAM_KEY";

    @BindView(R.id.model_name) TextView modelName;
    @BindView(R.id.recycler) RecyclerView recyclerView;
    @BindView(R.id.button) Button button;

    private FieldAdapter adapter;

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
        ButterKnife.bind(this, view);
        button.setOnClickListener(getOnValidateAndUpListener());
        setupArguments();
        setupAdapter();
        return view;
    }

    private View.OnClickListener getOnValidateAndUpListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onUpToPreviousModel();
            }
        };
    }

    private void setupArguments() {
        Bundle bundle = this.getArguments();
        String name = bundle.getString(QUERY_NAME_KEY);
        List<QLVariablesElement> parameters = (List<QLVariablesElement>) bundle.getSerializable(QUERY_PARAM_KEY);
        presenter.init(name, parameters);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    private void setupAdapter() {
        adapter = new FieldAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showQuery(String query) {
        ((SelectionActivity) getActivity()).updateQuery(query);
    }

    @Override
    public void showContinueButton(QLQuery query) {

    }

    @Override
    public void hideContinueButton() {

    }

    @Override
    public void displayModel(ModelViewModel model) {
        modelName.setText(trimPath(model.objectName()));
        adapter.setContentViewModels(model.fields());
    }

    @Override
    public void shouldDisplayBackButton(boolean shouldDisplay) {
        button.setVisibility(shouldDisplay ? View.VISIBLE : GONE);
    }

    @Override
    public void onFieldChecked(FieldViewModel model, boolean isChecked) {
        // TODO (kelianclerc) 8/6/17
        Toast.makeText(getContext(), model.fieldName() + (isChecked ? " checked" : " unchecked"), Toast.LENGTH_SHORT).show();

        try {
            presenter.onCheck(model, isChecked);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static Fragment makeFragment(QLQuery query) {
        AddElementFragment createQueryFragment = new AddElementFragment();
        Bundle bundle = new Bundle();
        bundle.putString(QUERY_NAME_KEY, query.getName());
        ArrayList<QLVariablesElement> params = new ArrayList<>(query.getParameters().getParams());
        bundle.putSerializable(QUERY_PARAM_KEY, params);
        createQueryFragment.setArguments(bundle);

        return createQueryFragment;
    }
}
