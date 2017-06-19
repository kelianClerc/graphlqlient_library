package com.applidium.graphql.client.data.net.retrofit;

import com.applidium.graphql.client.data.net.retrofit.model.RestUsers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GraphQL_AndroidService {

    @GET("users")
    Call<List<RestUsers>> getListOfUsers();
}
