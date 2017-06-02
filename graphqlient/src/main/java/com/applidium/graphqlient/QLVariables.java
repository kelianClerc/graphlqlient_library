package com.applidium.graphqlient;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.key;

public class QLVariables {
    public static final String SEPARATOR = ",";
    private static final String OPENING_CHARACTER = "{";
    private static final String CLOSING_CHARACTER = "}";
    Map<String, Object> variables;

    public QLVariables() {
        variables = new HashMap<>();
    }

    public QLVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void addVariable(String variableName, Object value) {
        if (variables.containsKey(variableName)) {
            variables.remove(variableName);
        }
        variables.put(variableName, value);
    }

    public boolean isNotPresent(String name) {
        return !variables.containsKey(key);
    }

    public String print() {
        String res = "";
        if (variables.size() <= 0) {
            return res;
        }
        int i = 0;
        res += OPENING_CHARACTER;
        for (String key : variables.keySet()) {
            res += "\"" + key + "\":";
            Object o = variables.get(key);
            if (o instanceof String) {
                res += "\"" + String.valueOf(o) + "\"";
            } else {
                res += String.valueOf(o);
            }
            if (i < variables.keySet().size() - 1) {
                res += SEPARATOR;
            }
            i++;
        }
        res += CLOSING_CHARACTER;
        return res;
    }
}
