package com.applidium.graphqlient;

public enum QLType {
    ID("ID"), STRING("String"), INT("Int"), FLOAT("Float"), BOOLEAN("Boolean");
    // TODO (kelianclerc) 23/5/17 how to add enums

    private final String id;

    private QLType(final String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
