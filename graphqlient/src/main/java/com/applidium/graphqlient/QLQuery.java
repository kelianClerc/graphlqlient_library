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
    // TODO (kelianclerc) 18/5/17 add field parameters
    // A request must not be anonymous if it has parameters.
    private final List<QLNode> queryFields = new ArrayList<>();

    public QLQuery() {
    }

    public QLQuery(String name) {
        this.name = name;
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
        stringBuilder.append(QUERY_OPENING_CHARACTER);
    }

    private void appendEnd(StringBuilder stringBuilder) {
        stringBuilder.append(QUERY_CLOSING_CHARACTER);
    }
}
