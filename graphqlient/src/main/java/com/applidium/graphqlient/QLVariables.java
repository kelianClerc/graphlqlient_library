package com.applidium.graphqlient;

import java.util.HashMap;
import java.util.Map;

public class QLVariables {
    public static final String SEPARATOR = ",";
    private static final String OPENING_CHARACTER = "{";
    private static final String CLOSING_CHARACTER = "}";
    Map<String, Object> parameters;

    public QLVariables() {
        parameters = new HashMap<>();
    }

    public QLVariables(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public String print() {
        String res = "";
        if (parameters.size() <= 0) {
            return res;
        }
        int i = 0;
        res += OPENING_CHARACTER;
        for (String key : parameters.keySet()) {
            res += "\"" + key + "\":";
            Object o = parameters.get(key);
            if (o instanceof String) {
                res += "\"" + String.valueOf(o) + "\"";
            } else {
                res += String.valueOf(o);
            }
            if (i < parameters.keySet().size() - 1) {
                res += SEPARATOR;
            }
            i++;
        }
        res += CLOSING_CHARACTER;
        return res;
    }
}
