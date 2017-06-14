package com.applidium.graphqlient;

import com.applidium.graphqlient.call.QLResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;

import io.norberg.automatter.gson.AutoMatterTypeAdapterFactory;
import okhttp3.ResponseBody;

public class QLMapper {

    private final Gson gson = new GsonBuilder()
        .registerTypeAdapterFactory(new AutoMatterTypeAdapterFactory())
        .create();

    public QLResponse convert(ResponseBody body, QLRequest query) throws IOException {

        String responseBody = body.string();
        JsonObject data = trimResponse(responseBody);
        String result = gson.toJson(data);
        QLResponseModel response = gson.fromJson(data, query.target());
        return new QLResponse(result, response);
    }

    private JsonObject trimResponse(String body) throws IOException {
        JsonElement jsonElement = gson.fromJson(body, JsonElement.class);
        return jsonElement.getAsJsonObject().get("data").getAsJsonObject();
    }
}
