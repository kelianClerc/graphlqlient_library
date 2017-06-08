package com.applidium.graphql.client.app.selection.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.selection.model.ModelMapper;
import com.applidium.graphql.client.app.selection.model.ModelViewModel;
import com.applidium.graphql.client.app.selection.ui.fragment.AddElementViewContract;
import com.applidium.graphql.client.core.entity.Endpoint;

import javax.inject.Inject;

public class AddElementPresenter extends Presenter<AddElementViewContract> {

    private final ModelMapper mapper;

    @Inject AddElementPresenter(AddElementViewContract view, ModelMapper mapper) {
        super(view);
        this.mapper = mapper;
    }

    @Override
    public void start() {
        handleModel(Endpoint.class);
    }

    private void handleModel(Class<?> model) {
        ModelViewModel viewModel = mapper.map(model);
        view.displayModel(viewModel);
    }

    @Override
    public void stop() {

    }
}
