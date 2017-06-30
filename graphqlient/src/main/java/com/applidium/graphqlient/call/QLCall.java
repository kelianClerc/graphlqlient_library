package com.applidium.graphqlient.call;

import com.applidium.graphqlient.QLRequest;
import com.applidium.graphqlient.converters.Converter;
import com.applidium.graphqlient.errorhandling.QLErrorsResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class QLCall<T> {

    public static final String ERRORS = "\"errors\":";
    private QLRequest query;
    private Call call;
    private Converter<T> converterFactory;

    public QLCall(QLRequest query, Call call, Converter<T> converterFactory) {
        this.query = query;
        this.call = call;
        this.converterFactory = converterFactory;
    }

    public Request request() {
        return call.request();
    }

    public QLResponse<T> execute() throws IOException {
        Response response = null;
        response = call.execute();
        return parseResponse(response);
    }

    private QLResponse<T> parseResponse(Response response) throws IOException {
        QLResponse<T> result = new QLResponse<>(response);

        int responseCode = response.code();
        if (responseCode < 200 || responseCode >= 300) {
            return result;
        }
        if (responseCode == 204 || responseCode == 205) {
            return result;
        }

        String responseBody = response.body().string();

        if (responseBody.replaceAll(" ", "").substring(1).startsWith(ERRORS)) {
            QLErrorsResponse errorsResponse = converterFactory.convertError(responseBody);
            result.setErrorResponse(true);
            result.setErrorsResponse(errorsResponse);
        } else {
            T convert = converterFactory.convert(responseBody);
            result.setResponse(convert);
        }
        return result;

    }

    public void enqueue(final QLCallback<T> responseCallback) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                responseCallback.onFailure(QLCall.this, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                QLResponse qlResponse = null;
                qlResponse = QLCall.this.parseResponse(response);
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
