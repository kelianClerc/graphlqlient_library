package com.applidium.graphqlient;

import com.applidium.graphqlient.call.QLResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import okhttp3.ResponseBody;

public class QLMapper {

    public QLResponse convert(ResponseBody body, QLQuery query) throws IOException {
        String string = body.string();
        System.out.println(string);
        Map<String, Object> mapJson = new Gson().fromJson(string, Map.class);
        String result = Arrays.toString(mapJson.entrySet().toArray());
        System.out.println(result);

        return new QLResponse(result);
    }
}
