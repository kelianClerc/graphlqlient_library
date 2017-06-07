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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

public class QLMapper {

    private final Gson gson = new Gson();

    public QLResponse convert(ResponseBody body, QLQuery query) throws IOException {

        Map responseMap = new HashMap();
        JsonObject data = trimResponse(body);
        for (int i = 0; i < query.getQueryFields().size(); i++) {
            parseJsonToObject(query.getQueryFields().get(i), responseMap, data);
        }
        String result = gson.toJson(data);
        return new QLResponse(result, responseMap);
    }

    private JsonObject trimResponse(ResponseBody body) throws IOException {
        JsonElement jsonElement = gson.fromJson(body.string(), JsonElement.class);
        return jsonElement.getAsJsonObject().get("data").getAsJsonObject();
    }

    private void parseJsonToObject(QLNode node, Map responseList, JsonObject data) {
        Class<? extends QLModel> type = node.getAssociatedObject();
        if (type != null && !type.equals(Object.class)) {
            String memberName = getMemberName(node);
            JsonElement json = data.get(memberName);
            Object parsedObject;
            if (json.isJsonArray()) {
                parsedObject = convertToList(type, json);
            } else {
                parsedObject = gson.fromJson(json, type);
            }
            responseList.put(memberName, parsedObject);
        }
    }

    private String getMemberName(QLNode node) {
        return node.getAlias() == null || node.getAlias().equals("")
               ? node.getName()
               : node.getAlias();
    }

    private List convertToList(Class<? extends QLModel> type, JsonElement json) {
        List resp = new ArrayList();
        JsonArray array = json.getAsJsonArray();
        for (int j = 0; j < array.size(); j++) {
            resp.add(gson.fromJson(array.get(j), type));
        }
        return resp;
    }
}
