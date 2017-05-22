package com.applidium.graphqlient.model;

import android.support.annotation.NonNull;

import com.applidium.graphqlient.annotations.Alias;
import com.applidium.graphqlient.annotations.Argument;
import com.applidium.graphqlient.annotations.Parameters;
import com.applidium.graphqlient.tree.QLElement;
import com.applidium.graphqlient.tree.QLLeaf;
import com.applidium.graphqlient.tree.QLNode;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QLModel {

    public QLNode createNodeFromField(Field field) {
        // TODO (kelianclerc) 22/5/17 handle field type being a list
        QLNode node = new QLNode(createElement(field));
        List<QLElement> children = new ArrayList<>();
        for (Field field1: field.getType().getDeclaredFields()) {
            appendQLElement(children, field1);
        }
        node.setAllChild(children);
        return node;
    }

    public void appendQLElement(List<QLElement> result, Field field) {
        if (isOfStandardType(field)) {
            result.add(createLeafFromField(field));
        } else if (QLModel.class.isAssignableFrom(field.getType())){
            result.add(createNodeFromField(field));
        }

    }

    private boolean isOfStandardType(Field field) {
        return field.getType() == String.class
            || field.getType() == Float.TYPE
            || field.getType() == Float.class
            || field.getType() == Boolean.class
            || field.getType() == Boolean.TYPE
            || field.getType() == Integer.class
            || field.getType() == Integer.TYPE
            || field.getType().isEnum();
    }

    @NonNull
    private QLLeaf createLeafFromField(Field field) {
        QLElement resultat = createElement(field);
        return new QLLeaf(resultat);
    }

    @NonNull
    private QLElement createElement(Field field) {
        String alias = null;
        String name = field.getName();
        Map<String, Object> parameters = new HashMap<>();
        for (Annotation annotatedElement : field.getDeclaredAnnotations()) {
            if (annotatedElement instanceof Alias) {
                alias = ((Alias) annotatedElement).name();
            } else if (annotatedElement instanceof Parameters) {
                createParametersMap(parameters, (Parameters) annotatedElement);
            }
        }
        return new QLElement(name, alias, parameters);
    }

    private void createParametersMap(Map<String, Object> parameters, Parameters annotatedElement) {
        for (Argument argument: annotatedElement.table()) {
            parameters.put(argument.argumentName(), argument.argumentValue());
        }
    }
}
