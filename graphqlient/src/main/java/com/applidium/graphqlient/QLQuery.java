package com.applidium.graphqlient;

import android.support.annotation.Nullable;

import com.applidium.graphqlient.tree.QLNode;

import java.util.ArrayList;
import java.util.List;

public class QLQuery {

    private static final String QUERY_KEYWORD = "query";
    private static final String QUERY_OPENING_CHARACTER = "{";
    private static final String QUERY_CLOSING_CHARACTER = "}";

    @Nullable String name;
    @Nullable List<QLVariablesElement> parameters;
    // TODO (kelianclerc) 18/5/17 add field parameters
    // A request must not be anonymous if it has parameters.
    private final List<QLNode> queryFields = new ArrayList<>();

    public QLQuery() {
    }

    public QLQuery(String name) {
        this.name = name;
    }
    public QLQuery(String name, List<QLVariablesElement> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public void append(QLNode element) {
        if (element != null) {
            queryFields.add(element);
        }
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
    public List<QLVariablesElement> getParameters() {
        return parameters;
    }

    public void setParameters(@Nullable List<QLVariablesElement> parameters) {
        this.parameters = parameters;
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
        if (parameters != null && parameters.size() > 0) {
            appendQueryParams(stringBuilder);
        }
        stringBuilder.append(QUERY_OPENING_CHARACTER);
    }

    private void appendQueryParams(StringBuilder stringBuilder) {
        stringBuilder.append("(");
        int i = 0;
        for (QLVariablesElement element: parameters) {
            stringBuilder.append(element.print());
            if (i < parameters.size() - 1) {
                stringBuilder.append(",");
            }
            i++;
        }
        stringBuilder.append(")");
    }

    private void appendEnd(StringBuilder stringBuilder) {
        stringBuilder.append(QUERY_CLOSING_CHARACTER);
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }
}
