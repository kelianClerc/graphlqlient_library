package com.applidium.graphqlient;

public class QLVariablesElement {
    public static final String MANDATORY_CHARACTER = "!";
    public static final String VARIABLE_CHARACTER = "$";
    private String name;
    private QLType type;
    private boolean isMandatory;

    public QLVariablesElement() {
    }

    public QLVariablesElement(String name) {
        this.name = name;
    }

    public QLVariablesElement(String name, QLType type) {
        this.name = name;
        this.type = type;
        isMandatory = false;
    }

    public QLVariablesElement(String name, QLType type, boolean isMandatory) {
        this.name = name;
        this.type = type;
        this.isMandatory = isMandatory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QLType getType() {
        return type;
    }

    public void setType(QLType type) {
        this.type = type;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public String printVariableName() {
        return VARIABLE_CHARACTER + name;
    }

    public String print() {
        String res = "";
        res += printVariableName() + ":";
        res += type.toString();
        if (isMandatory) {
            res += MANDATORY_CHARACTER;
        }
        return res;
    }
}