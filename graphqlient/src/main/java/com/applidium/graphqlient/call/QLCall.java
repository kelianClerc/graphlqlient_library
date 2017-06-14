package com.applidium.graphqlient.call;

import com.applidium.graphqlient.QLMapper;
import com.applidium.graphqlient.QLRequest;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class QLCall {

    private QLRequest query;
    private Call call;
    private QLMapper mapper;

    public QLCall(QLRequest query, Call call) {
        this.query = query;
        this.call = call;
        mapper = new QLMapper();
    }

    public Request request() {
        return call.request();
    }

    public QLResponse execute() throws IOException, JSONException {
        Response response = call.execute();
        return parseResponse(response);
    }

    private QLResponse parseResponse(Response response) throws IOException, JSONException {
        int responseCode = response.code();
        if (responseCode < 200 || responseCode >= 300) {
            // TODO (kelianclerc) 1/6/17 parse error
            return null;
        }
        if (responseCode == 205) {
            // TODO (kelianclerc) 1/6/17 empty response
            return null;
        }

        return mapper.convert(response.body(), query);

    }

    public void enqueue(final QLCallback responseCallback) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                responseCallback.onFailure(QLCall.this, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                QLResponse qlResponse = null;
                try {
                    qlResponse =QLCall.this.parseResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new IOException("Not valid json received");
                }
                responseCallback.onResponse(QLCall.this, qlResponse);
            }
        });
    }

    public void cancel() {
        call.cancel();
    }

    public boolean isExecuted() {
        return call.isExecuted();
    }

    public boolean isCanceled() {
        return call.isCanceled();
    }

    public QLRequest getQuery() {
        return query;
    }

    public Call getCall() {
        return call;
    }
}
