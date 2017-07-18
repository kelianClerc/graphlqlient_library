package com.applidium.graphqlient.errorhandling;

import java.util.Arrays;
import java.util.List;

public class QLError {
    String message;
    List<String> fields;

    public QLError(String message, List<String> fields) {
        this.message = message;
        this.fields = fields;
    }

    private QLError(Builder builder) {
        setMessage(builder.message);
        setFields(builder.fields);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public static final class Builder {
        private String message;
        private List<String> fields;

        public Builder() {
        }

        public Builder message(String val) {
            message = val;
            return this;
        }

        public Builder fields(List<String> val) {
            fields = val;
            return this;
        }

        public QLError build() {
            return new QLError(this);
        }
    }

    @Override
    public String toString() {
        return "- Error : " + message + " at field " + Arrays.toString(fields.toArray());
    }
}
