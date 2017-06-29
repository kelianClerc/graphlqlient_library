package com.applidium.graphqlient;

import android.support.annotation.Nullable;

import com.applidium.graphqlient.tree.QLFragment;
import com.applidium.graphqlient.tree.QLNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QLQuery {

    private static final String QUERY_KEYWORD = "query";
    private static final String QUERY_OPENING_CHARACTER = "{";
    private static final String QUERY_CLOSING_CHARACTER = "}";

    private List<QLFragment> fragments;
    @Nullable private String name;
    private final QLParameters parameters = new QLParameters();
    private final QLVariables variables = new QLVariables();
    // A request must not be anonymous if it has variables.
    private final List<QLNode> queryFields = new ArrayList<>();

    public QLQuery() {
        fragments = new ArrayList<>();
    }

    public QLQuery(String name) {
        super();
        this.name = name;
    }
    public QLQuery(String name, List<QLVariablesElement> parameters) {
        super();
        this.name = name;
        this.parameters.setParams(parameters);
    }

    public void append(QLNode element) {
        if (element != null) {
            queryFields.add(element);
        }
    }

    public void addVariable(String variableName, Object value) {
        QLType varType = parameters.getType(variableName);
        if (varType == null) {
            // TODO (kelianclerc) 2/6/17 exception variable not declared
            return;
        }
        variables.addVariable(variableName, value);
    }

    public boolean isVariableEmpty() {
        return variables.isEmpty();
    }

    public boolean areAllParametersGiven() {
        return parameters.allParametersGiven(variables);
    }

    public String printQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        appendHeader(stringBuilder);
        for (QLNode node : queryFields) {
            stringBuilder.append(node.print());
        }
        appendEnd(stringBuilder);
        return stringBuilder.toString();
    }

    private void appendHeader(StringBuilder stringBuilder) {
        if (name != null) {
            stringBuilder.append(QUERY_KEYWORD + " " + name);
        }
        if (parameters != null && !parameters.isEmpty()) {
            appendQueryParams(stringBuilder);
        }
        stringBuilder.append(QUERY_OPENING_CHARACTER);
    }

    private void appendQueryParams(StringBuilder stringBuilder) {
        stringBuilder.append("(");
        stringBuilder.append(parameters.printParameters());
        stringBuilder.append(")");
    }

    public void setQueryFields(List<QLNode> queryFields){
        this.queryFields.clear();
        if (queryFields != null) {
            this.queryFields.addAll(queryFields);
        }
    }

    public List<QLNode> getQueryFields() {
        return queryFields;
    }

    @Nullable
    public QLParameters getParameters() {
        return parameters;
    }

    public void setParameters(QLParameters parameters) {
        this.parameters.setParams(parameters.getParams());
    }
    public void setParameters(List<QLVariablesElement> parameters) {
        this.parameters.setParams(parameters);
    }


    private void appendEnd(StringBuilder stringBuilder) {
        stringBuilder.append(QUERY_CLOSING_CHARACTER);
    }



    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public QLVariables getVariables() {
        return variables;
    }

    public void setVariables(QLVariables variables) {
        Map<String, Object> vars = variables.getVariables();
        for (String key: vars.keySet()) {
            addVariable(key, vars.get(key));
        }
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public List<QLFragment> getFragments() {
        return fragments;
    }

    public void setFragments(List<QLFragment> fragments) {
        this.fragments = fragments;
    }
}
