package com.applidium.graphqlient;

import com.applidium.graphqlient.call.QLResponse;
import com.applidium.graphqlient.model.QLModel;
import com.applidium.graphqlient.tree.QLNode;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class QLMapper {

    public QLResponse convert(ResponseBody body, QLQuery query) throws IOException {
        String string = body.string();
        System.out.println(string);

        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(string, JsonElement.class);
        List<QLModel> responseList = new ArrayList<>();
        JsonObject data = jsonElement.getAsJsonObject().get("data").getAsJsonObject();
        for (int i = 0; i < query.getQueryFields().size(); i++) {
            QLNode node = query.getQueryFields().get(i);
            Class<? extends QLModel> type = node.getAssociatedObject();
            if (!type.equals(Object.class)) {
                responseList.add(gson.fromJson(data.get(node.getAlias() == null || node.getAlias().equals("") ? node.getName() : node.getAlias()), type));
            }
        }
        String result = gson.toJson(jsonElement);
        System.out.println(result);

        return new QLResponse(result, responseList);
    }
}
