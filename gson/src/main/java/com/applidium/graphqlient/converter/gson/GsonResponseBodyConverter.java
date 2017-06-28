package com.applidium.graphqlient.converter.gson;

import com.applidium.graphqlient.converters.Converter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;

class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String responseBody = null;
        responseBody = value.string();
        JsonElement data = null;
        data = trimResponse(responseBody);
        return adapter.fromJsonTree(data);
    }

    private JsonElement trimResponse(String body) {
        JsonElement jsonElement = gson.fromJson(body, JsonElement.class);
        return jsonElement.getAsJsonObject().get("data").getAsJsonObject();
    }
}
