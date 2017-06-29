package com.applidium.graphqlient.call;

import com.applidium.graphqlient.QLRequest;
import com.applidium.graphqlient.converters.Converter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class QLCall {

    private QLRequest query;
    private Call call;
    private Converter<ResponseBody, ?> converterFactory;

    public QLCall(QLRequest query, Call call, Converter<ResponseBody, ?> converterFactory) {
        this.query = query;
        this.call = call;
        this.converterFactory = converterFactory;
    }

    public Request request() {
        return call.request();
    }

    public QLResponse execute() throws IOException {
        Response response = null;
        response = call.execute();
        return parseResponse(response);
    }

    private QLResponse parseResponse(Response response) throws IOException {
        int responseCode = response.code();
        if (responseCode < 200 || responseCode >= 300) {
            // TODO (kelianclerc) 1/6/17 parse error
            return null;
        }
        if (responseCode == 205) {
            // TODO (kelianclerc) 1/6/17 empty response
            return null;
        }

        QLResponse result = new QLResponse(response, converterFactory.convert(response.body()));
        return result;

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
                qlResponse =QLCall.this.parseResponse(response);
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
