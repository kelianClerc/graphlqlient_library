package com.applidium.graphqlient.tree;

import android.support.annotation.NonNull;

import com.applidium.graphqlient.QLVariablesElement;
import com.applidium.graphqlient.annotations.Alias;
import com.applidium.graphqlient.annotations.Argument;
import com.applidium.graphqlient.annotations.Parameters;
import com.applidium.graphqlient.model.QLModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QLTreeBuilder {

    public QLNode createNodeFromField(Field field) {
        QLNode node = new QLNode(createElement(field));
        List<QLElement> children = new ArrayList<>();
        Class<?> fieldType = getFieldType(field);
        for (Field field1: fieldType.getDeclaredFields()) {
            appendQLElement(children, field1);
        }
        node.addAllChild(children);
        return node;
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
            if (argument.argumentVariable().length() > 0) {
                parameters.put(argument.argumentName(), new QLVariablesElement(argument.argumentVariable()));
            } else{
                parameters.put(argument.argumentName(), argument.argumentValue());
            }
        }
    }

    private Class<?> getFieldType(Field field) {
        Class<?> fieldType = field.getType();
        if (Collection.class.isAssignableFrom(field.getType())) {
            ParameterizedType genericType = (ParameterizedType) field.getGenericType();
            fieldType = (Class<?>)(genericType.getActualTypeArguments()[0]);
        }
        return fieldType;
    }

    public void appendQLElement(List<QLElement> result, Field field) {
        if (isOfStandardType(field)) {
            result.add(createLeafFromField(field));
        } else if (QLModel.class.isAssignableFrom(field.getType())){
            result.add(createNodeFromField(field));
        }

    }

    private boolean isOfStandardType(Field field) {
        // TODO (kelianclerc) 23/5/17 How to allow user to add its how enums
        Class<?> fieldType = getFieldType(field);
        return fieldType == String.class
            || fieldType == Float.TYPE
            || fieldType == Float.class
            || fieldType == Boolean.class
            || fieldType == Boolean.TYPE
            || fieldType == Integer.class
            || fieldType == Integer.TYPE
            || fieldType.isEnum();
    }

    @NonNull
    private QLLeaf createLeafFromField(Field field) {
        QLElement resultat = createElement(field);
        return new QLLeaf(resultat);
    }
}