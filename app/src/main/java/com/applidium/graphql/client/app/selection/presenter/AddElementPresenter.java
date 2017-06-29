package com.applidium.graphql.client.app.selection.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.selection.model.FieldViewModel;
import com.applidium.graphql.client.app.selection.model.FieldViewModelBuilder;
import com.applidium.graphql.client.app.selection.model.ModelMapper;
import com.applidium.graphql.client.app.selection.model.ModelViewModel;
import com.applidium.graphql.client.app.selection.ui.fragment.AddElementViewContract;
import com.applidium.graphql.client.core.entity.Endpoint;
import com.applidium.graphqlient.QLQuery;
import com.applidium.graphqlient.QLVariablesElement;
import com.applidium.graphqlient.model.QLModel;
import com.applidium.graphqlient.tree.QLElement;
import com.applidium.graphqlient.tree.QLNode;
import com.applidium.graphqlient.tree.QLTreeBuilder;

import java.lang.reflect.Member;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AddElementPresenter extends Presenter<AddElementViewContract> {

    private final QLTreeBuilder treeBuilder = new QLTreeBuilder();
    private final QLQuery qlQuery = new QLQuery();

    private final ModelMapper mapper;
    private final List<ModelViewModel> modelStack = new ArrayList<>();
    private final List<QLNode> nodeStack = new ArrayList<>();
    private  QLNode currentNode;
    boolean isEndpointModelDisplayed = true;

    @Inject AddElementPresenter(AddElementViewContract view, ModelMapper mapper) {
        super(view);
        this.mapper = mapper;
    }

    @Override
    public void start() {
        handleModel(Endpoint.class);
    }

    private void handleModel(Class<?> model) {
        if (model == Endpoint.class) {
            isEndpointModelDisplayed = true;
        } else {
            isEndpointModelDisplayed = false;
        }
        ModelViewModel viewModel = mapper.map(model);
        modelStack.add(viewModel);
        view.displayModel(viewModel);
        view.shouldDisplayBackButton(!isEndpointModelDisplayed);
    }

    @Override
    public void stop() {

    }

    public void onCheck(FieldViewModel fieldViewModel, boolean isChecked) throws
        ClassNotFoundException, NoSuchMethodException, NoSuchFieldException {
        fieldViewModel = FieldViewModelBuilder.from(fieldViewModel).isChecked(isChecked).build();
        Member declaredField = null;
        Class<?> aClass = Class.forName(modelStack.get(modelStack.size() - 1).objectName());
        try {
            declaredField = aClass.getDeclaredField(fieldViewModel.fieldName());
        } catch (NoSuchFieldException e) {
            declaredField = aClass.getDeclaredMethod(fieldViewModel.fieldName());
        }
        if (isChecked) {
            processCheckedField(fieldViewModel, declaredField);
        } else {
            processUncheckedField(declaredField);
        }

        qlQuery.setQueryFields(nodeStack);
        updatePreview();
    }

    private void updatePreview() {
        view.showQuery(qlQuery.printQuery());
    }

    private void processCheckedField(FieldViewModel fieldViewModel, Member declaredField) {
        if (currentNode == null) {
            initNodeStack(declaredField);
        } else {
            setAsChildrenOfCurrentNode(declaredField);
        }
        if (QLModel.class.isAssignableFrom(getMemberClass(fieldViewModel))) {
            handleModel(getMemberClass(fieldViewModel));
        }
    }

    private Class<?> getMemberClass(FieldViewModel fieldViewModel) {
        if (fieldViewModel.type() instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) fieldViewModel.type();
            Type geneType = paramType.getActualTypeArguments()[0];
            return (Class<?>) geneType;
        }
        return (Class<?>)fieldViewModel.type();
    }

    private void initNodeStack(Member declaredField) {
        nodeStack.add(treeBuilder.createNodeFromField(declaredField, false));
        currentNode = nodeStack.get(0);
    }

    private void setAsChildrenOfCurrentNode(Member declaredField) {
        treeBuilder.appendQLElement(currentNode.getChildren(), declaredField, false);
        QLElement element = currentNode.getChildren().get(currentNode.getChildren().size() - 1);
        if (element instanceof QLNode) {
            currentNode = (QLNode) element;
        }
    }

    private void processUncheckedField(Member declaredField) {
        removeFromCurrentNodeStack(declaredField);
    }

    private void removeFromCurrentNodeStack(Member declaredField) {
        QLNode node = currentNode;
        QLElement target = null;
        for (QLElement element : node.getChildren()) {
            if (element.getName().equals(declaredField.getName())) {
                target = element;
                break;
            }
        }
        if (target != null) {
            node.removeChild(target);
        }
    }

    public void init(String name, List<QLVariablesElement> parameters) {
        qlQuery.setName(name);
        qlQuery.setParameters(parameters);
    }

    public void onUpToPreviousModel() {
        modelStack.remove(modelStack.size() - 1);
        ModelViewModel model = modelStack.get(modelStack.size() - 1);
        view.displayModel(model);
        view.shouldDisplayBackButton(!model.objectName().contains("Endpoint"));
        if (nodeStack.contains(currentNode)) {
            currentNode = null;
        }
    }
}
