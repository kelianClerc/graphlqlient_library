package com.applidium.graphqlient;

import com.applidium.graphqlient.call.QLResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.norberg.automatter.gson.AutoMatterTypeAdapterFactory;
import okhttp3.ResponseBody;

public class QLMapper {

    private final Gson gson = new GsonBuilder()
        .registerTypeAdapterFactory(new AutoMatterTypeAdapterFactory())
        .create();

    public QLResponse convert(ResponseBody body, QLRequest query) throws IOException {

        Map responseMap = new HashMap();
        JsonObject data = trimResponse(body);
        String result = gson.toJson(data);
        QLResponseModel response = gson.fromJson(body.string(), query.target());
        return new QLResponse(result, response);
    }

    private JsonObject trimResponse(ResponseBody body) throws IOException {
        JsonElement jsonElement = gson.fromJson(body.string(), JsonElement.class);
        return jsonElement.getAsJsonObject().get("data").getAsJsonObject();
    }
}
