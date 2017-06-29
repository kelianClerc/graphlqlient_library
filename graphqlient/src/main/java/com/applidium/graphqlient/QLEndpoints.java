package com.applidium.graphqlient;

import com.applidium.graphqlient.model.QLModel;
import com.applidium.graphqlient.tree.QLNode;
import com.applidium.graphqlient.tree.QLTreeBuilder;

public class QLEndpoints {
    private Class<? extends QLModel> endpoints;
    private QLTreeBuilder treeBuilder = new QLTreeBuilder();

    public QLEndpoints(Class<? extends QLModel> endpoints) {
        this.endpoints = endpoints;
    }

    public QLNode get(String fieldName) {
        try {
            if (endpoints.getDeclaredFields().length == 0) {
                return treeBuilder.createNodeFromField(endpoints.getDeclaredMethod(fieldName));
            }
            return treeBuilder.createNodeFromField(endpoints.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
