package com.applidium.graphqlient.tree;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class QLElement {

    private String name;
    private final Map<String, Object> parameters = new HashMap<>();
    @Nullable private String alias;

    public QLElement(String name) {
        this.name = name;
    }

    public QLElement(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public QLElement(String name, Map<String, Object> parameters) {
        this.name = name;
        this.parameters.clear();
        this.parameters.putAll(parameters);
    }

    public QLElement(String name, String alias,  Map<String, Object> parameters) {
        this.name = name;
        this.alias = alias;
        this.parameters.clear();
        this.parameters.putAll(parameters);
    }

    public String print() {
        String result = "";
        result += computeAlias();
        result += name;
        result += computeParams();
        return result;
    }

    private String computeAlias() {
        if (alias == null) {
            return "";
        }
        return alias + ":";
    }

    private String computeParams() {
        if (parameters == null || parameters.size() <= 0) {
            return "";
        }
        String result = "";
        result += "(";
        int i = 0;
        for (String key: parameters.keySet()) {
            result += key + ":"+ computeParamValue(key);
            if (i < parameters.size() - 1) {
                result += ",";
            }
            i++;
        }
        result += ")";
        return result;
    }

    private String computeParamValue(String key) {
        // TODO (kelianclerc) 19/5/17 handle params being dependent of variable field of request
        // http://graphql.org/learn/queries/#variables
        Object o = parameters.get(key);
        if (o instanceof String) {
            return "\"" + String.valueOf(o) + "\"";
        }
        return String.valueOf(o);
    }
}
