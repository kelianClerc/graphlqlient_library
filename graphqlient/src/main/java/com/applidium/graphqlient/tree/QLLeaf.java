package com.applidium.graphqlient.tree;

import java.util.Map;

public class QLLeaf extends QLElement {

    public QLLeaf(String name) {
        super(name);
    }

    public QLLeaf(String name, String alias) {
        super(name, alias);
    }
    public QLLeaf(String name, Map<String, Object> params) {
        super(name, params);
    }
    public QLLeaf(String name, String alias, Map<String, Object> params) {
        super(name, alias, params);
    }
}
