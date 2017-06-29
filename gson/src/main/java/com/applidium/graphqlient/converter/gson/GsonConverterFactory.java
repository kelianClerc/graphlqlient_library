package com.applidium.graphqlient.converter.gson;

import com.applidium.graphqlient.converters.Converter;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;

public class GsonConverterFactory extends Converter.Factory {
    public static GsonConverterFactory create() {
        return create(new Gson());
    }
    public static GsonConverterFactory create(Gson gson) {
        return new GsonConverterFactory(gson);
    }

    private final Gson gson;

    private GsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyConverter(gson, adapter);
    }
}
