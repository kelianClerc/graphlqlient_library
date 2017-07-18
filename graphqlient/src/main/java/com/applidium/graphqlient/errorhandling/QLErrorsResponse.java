package com.applidium.graphqlient.errorhandling;

import com.applidium.graphqlient.QLResponseModel;

import java.util.Arrays;
import java.util.List;

public class QLErrorsResponse implements QLResponseModel {
    private List<QLError> errors;

    private QLErrorsResponse(Builder builder) {
        errors = builder.errors;
    }

    public QLErrorsResponse(List<QLError> errors) {
        this.errors = errors;
    }

    public List<QLError> getErrors() {
        return errors;
    }

    public void setErrors(List<QLError> errors) {
        this.errors = errors;
    }

    public static final class Builder {
        private List<QLError> errors;

        public Builder() {
        }

        public Builder errors(List<QLError> val) {
            errors = val;
            return this;
        }

        public QLErrorsResponse build() {
            return new QLErrorsResponse(this);
        }
    }

    @Override
    public String toString() {
        return "QLErrorsResponse{" +
            "errors=" + Arrays.toString(errors.toArray()) +
            '}';
    }
}
