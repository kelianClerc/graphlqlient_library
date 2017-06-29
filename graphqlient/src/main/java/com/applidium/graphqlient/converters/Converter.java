package com.applidium.graphqlient.converters;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

public interface Converter<F, T> {
    T convert(F value) throws IOException;

    abstract class Factory {
        public Converter<ResponseBody, ?> responseBodyConverter(Type type) {
            return null;
        }
    }
}
