package com.applidium.graphqlient.tree;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.applidium.graphqlient.QLVariablesElement;
import com.applidium.graphqlient.annotations.Alias;
import com.applidium.graphqlient.annotations.Argument;
import com.applidium.graphqlient.annotations.Parameters;
import com.applidium.graphqlient.model.QLModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QLTreeBuilder {

    public QLNode createNodeFromField(Member member) {
        QLNode node = new QLNode(createElement(member));
        List<QLElement> children = new ArrayList<>();
        Class<?> fieldType = getFieldType(member);
        Field[] declaredFields = fieldType.getDeclaredFields();
        if (declaredFields.length > 0) {
            for (Field field1: declaredFields) {
                appendQLElement(children, field1);
            }
        } else {
            for (Method method: fieldType.getDeclaredMethods()) {
                appendQLElement(children, method);
            }
        }

        node.addAllChild(children);
        node.setAssociatedObject(fieldType);
        return node;
    }

    @NonNull
    private QLElement createElement(Member member) {
        String alias = null;
        String name = member.getName();
        Map<String, Object> parameters = new HashMap<>();
        Member target = getMemberCorrectClass(member);

        for (Annotation annotatedElement : getDeclaredAnnotations(target)) {
            if (annotatedElement instanceof Alias) {
                alias = ((Alias) annotatedElement).name();
            } else if (annotatedElement instanceof Parameters) {
                createParametersMap(parameters, (Parameters) annotatedElement);
            }
        }
        return new QLElement(name, alias, parameters);
    }

    private Annotation[] getDeclaredAnnotations(Member member) {
        if (member instanceof Field) {
            return ((Field) member).getDeclaredAnnotations();
        } else if (member instanceof Method) {
            return ((Method) member).getDeclaredAnnotations();
        } else {
            // TODO (kelianclerc) 7/6/17 exception
            return null;
        }
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

    private Class<?> getFieldType(Member member) {
        Member target = getMemberCorrectClass(member);
        Class<?> fieldType = getType(target);
        if (Collection.class.isAssignableFrom(fieldType)) {
            ParameterizedType genericType = (ParameterizedType) getGenericType(target);
            fieldType = (Class<?>)(genericType.getActualTypeArguments()[0]);
        }
        return fieldType;
    }

    public void appendQLElement(List<QLElement> result, Member member) {
        Member target = getMemberCorrectClass(member);
        if (target == null) return;
        if (isOfStandardType(target)) {
            result.add(createLeafFromField(target));
        } else if (QLModel.class.isAssignableFrom(getType(target))){
            result.add(createNodeFromField(target));
        }

    }

    @Nullable
    private Member getMemberCorrectClass(Member member) {
        Member target;
        if (member instanceof Field) {
            target = (Field) member;
        } else if (member instanceof Method) {
            target = (Method) member;
        } else {
            return null;
        }
        return target;
    }

    private Class<?> getType(Member member) {
        if (member instanceof Field) {
            return ((Field) member).getType();
        } else if (member instanceof Method) {
            return ((Method) member).getReturnType();
        } else {
            // TODO (kelianclerc) 7/6/17 exception
            return null;
        }
    }

    private Type getGenericType(Member member) {
        if (member instanceof Field) {
            return ((Field) member).getGenericType();
        } else if (member instanceof Method) {
            return ((Method) member).getGenericReturnType();
        } else {
            // TODO (kelianclerc) 7/6/17 exception
            return null;
        }
    }

    private boolean isOfStandardType(Member field) {
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
    private QLLeaf createLeafFromField(Member field) {
        QLElement resultat = createElement(field);
        return new QLLeaf(resultat);
    }

    public void propagateType(QLNode node) {
        Class<?> associatedObject = node.getAssociatedObject();
        if (associatedObject == Object.class) {
            return;
        }

        for (QLElement element : node.getChildren()) {
            if (!(element instanceof QLNode)) {
                break;
            }
            QLNode elementToUpdate = (QLNode) element;
            elementToUpdate.setAssociatedObject(findFieldTypeByName(associatedObject, element.getName(), element.getAlias()));

        }
    }

    private Class<?> findFieldTypeByName(Class<?> type, String name, String alias) {
        for (Field field : type.getFields()) {
            if (field.getName().equals(name) || field.getName().equals(alias)) {
                return field.getType();
            }
        }
        return Object.class;
    }
}
