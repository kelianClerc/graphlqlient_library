package com.applidium.graphqlient.tree;

import java.util.ArrayList;
import java.util.List;

public class QLNode extends QLElement {

    private static final String OPENING_CHARACTER = "{";
    private static final String CLOSING_CHARACTER = "}";
    private static final String SEPARATION_SUBFIELD_CHARACTER = ",";
    private final ArrayList<QLElement> children = new ArrayList<>();

    public QLNode(String name) {
        super(name);
    }

    public void addChild(QLElement child) {
        children.add(child);
    }

    public void removeChild(QLElement child) {
        children.remove(child);
    }

    public List<QLElement> getChildren() {
        return children;
    }

    public String getElementInfo() {
        return super.print();
    }

    @Override
    public String print() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getElementInfo());
        if (getChildren().size() > 0) {
            appendChildrens(getChildren(), stringBuilder);
        }
        return stringBuilder.toString();
    }

    private static void appendChildrens(List<QLElement> node, StringBuilder stringBuilder) {
        stringBuilder.append(OPENING_CHARACTER);
        for (int i = 0; i < node.size() - 1; i++) {
            QLElement nodeChild = node.get(i);
            stringBuilder.append(nodeChild.print());
            stringBuilder.append(SEPARATION_SUBFIELD_CHARACTER);
        }
        stringBuilder.append(node.get(node.size()-1).print());
        stringBuilder.append(CLOSING_CHARACTER);
    }
}