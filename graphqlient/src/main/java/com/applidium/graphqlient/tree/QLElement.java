package com.applidium.graphqlient.tree;

public class QLElement {

    private String name;
    // TODO (kelianclerc) 19/5/17 add parameters
    // TODO (kelianclerc) 19/5/17 add alias 

    public QLElement(String name) {
        this.name = name;
    }

    public String print() {
        return name;
    }
}
