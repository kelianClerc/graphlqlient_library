package com.applidium.graphqlient.converter.gson;

import com.applidium.graphqlient.converters.Converter;
import com.applidium.graphqlient.errorhandling.QLErrorsResponse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

class GsonResponseBodyConverter<T> implements Converter<T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private final TypeAdapter<QLErrorsResponse> errorAdapter;

    public GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
        errorAdapter =  gson.getAdapter(TypeToken.get(QLErrorsResponse.class));
    }

    @Override
    public T convert(String value) throws IOException {
        JsonElement data = null;
        data = trimResponse(value);
        return adapter.fromJsonTree(data);
    }

    @Override
    public QLErrorsResponse convertError(String response) throws IOException {
        JsonElement element = gson.fromJson(response, JsonElement.class);
        return errorAdapter.fromJsonTree(element);
    }

    private JsonElement trimResponse(String body) {
        JsonElement jsonElement = gson.fromJson(body, JsonElement.class);
        return jsonElement.getAsJsonObject().get("data").getAsJsonObject();
    }
}
