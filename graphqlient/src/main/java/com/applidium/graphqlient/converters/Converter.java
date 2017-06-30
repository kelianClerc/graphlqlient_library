package com.applidium.graphqlient.converters;

import com.applidium.graphqlient.errorhandling.QLErrorsResponse;

import java.io.IOException;
import java.lang.reflect.Type;

public interface Converter<T> {
    T convert(String value) throws IOException;
    QLErrorsResponse convertError(String response) throws IOException;

    abstract class Factory {
        public Converter<?> responseBodyConverter(Type type) {
            return null;
        }
    }
}
