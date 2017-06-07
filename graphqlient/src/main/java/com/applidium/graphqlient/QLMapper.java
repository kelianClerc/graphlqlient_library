package com.applidium.graphqlient;

import com.applidium.graphqlient.call.QLResponse;
import com.applidium.graphqlient.model.QLModel;
import com.applidium.graphqlient.tree.QLNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class QLMapper {

    private final Gson gson = new Gson();

    public QLResponse convert(ResponseBody body, QLQuery query) throws IOException {

        List responseList = new ArrayList<>();
        JsonObject data = trimResponse(body);
        for (int i = 0; i < query.getQueryFields().size(); i++) {
            parseJsonToObject(query.getQueryFields().get(i), responseList, data);
        }
        String result = gson.toJson(data);
        return new QLResponse(result, responseList);
    }

    private JsonObject trimResponse(ResponseBody body) throws IOException {
        JsonElement jsonElement = gson.fromJson(body.string(), JsonElement.class);
        return jsonElement.getAsJsonObject().get("data").getAsJsonObject();
    }

    private void parseJsonToObject(QLNode node, List responseList, JsonObject data) {
        Class<? extends QLModel> type = node.getAssociatedObject();
        if (!type.equals(Object.class)) {
            JsonElement json = data.get(getMemberName(node));
            if (json.isJsonArray()) {
                convertToList(responseList, type, json);
            } else {
                responseList.add(gson.fromJson(json, type));
            }
        }
    }

    private String getMemberName(QLNode node) {
        return node.getAlias() == null || node.getAlias().equals("")
               ? node.getName()
               : node.getAlias();
    }

    private void convertToList(List responseList, Class<? extends QLModel> type, JsonElement json) {
        List resp = new ArrayList();
        JsonArray array = json.getAsJsonArray();
        for (int j = 0; j < array.size(); j++) {
            resp.add(gson.fromJson(array.get(j), type));
        }
        responseList.add(resp);
    }
}
