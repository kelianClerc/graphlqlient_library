package com.applidium.graphql.client.data.net.common;

import com.applidium.graphql.client.core.error.exceptions.ServerClientException;
import com.applidium.graphql.client.core.error.exceptions.UnexpectedException;
import com.applidium.graphql.client.core.error.exceptions.NetworkException;
import com.applidium.graphql.client.core.error.exceptions.ServerException;
import com.applidium.graphql.client.data.net.retrofit.model.RestError;
import com.applidium.graphql.client.utils.logging.Logger;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;

@Singleton
public class RequestManager {

    private final Converter<ResponseBody, RestError> errorConverter;
    private final Logger logger;
    private final CodeHelper codeHelper;

    @Inject RequestManager(
        Converter<ResponseBody, RestError> errorConverter,
        Logger logger,
        CodeHelper codeHelper
    ) {
        this.errorConverter = errorConverter;
        this.logger = logger;
        this.codeHelper = codeHelper;
    }

    public <T> T tryToDoRequest(
        Call<T> call
    ) throws NetworkException, ServerException, UnexpectedException, ServerClientException {
        try {
            return doRequest(call);
        } catch (SocketException e) {
            logger.v(this, e, "Throwing NetworkException");
            throw new NetworkException();
        } catch (MalformedJsonException | JsonSyntaxException | EOFException e) {
            logger.v(this, e, "Throwing ServerError");
            throw new ServerException();
        } catch (IOException e) {
            logger.v(this, e, "Throwing UnexpectedError");
            throw new UnexpectedException();
        }
    }

    private <T> T doRequest(Call<T> call)
        throws IOException, ServerException, UnexpectedException, ServerClientException {
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        int code = response.code();
        if (codeHelper.clientError(code)) {
            RestError error = tryToConvertErrorBody(response.errorBody());
            String message = null;
            if (error != null) {
                message = error.error();
            }
            throw new ServerClientException(message);
        }
        if (codeHelper.serverError(code)) {
            throw new ServerException();
        }
        throw new UnexpectedException();
    }

    private RestError tryToConvertErrorBody(ResponseBody errorBody) {
        try {
            return errorConverter.convert(errorBody);
        } catch (IOException e) {
            logger.v(this, e, "Can't convert error body from response %s", errorBody);
            return null;
        }
    }
}
