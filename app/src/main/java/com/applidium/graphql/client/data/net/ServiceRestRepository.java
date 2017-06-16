package com.applidium.graphql.client.data.net;

import com.applidium.graphql.client.core.boundary.RestRepository;
import com.applidium.graphql.client.core.entity.User;
import com.applidium.graphql.client.data.net.retrofit.GraphQL_AndroidService;
import com.applidium.graphql.client.data.net.retrofit.model.RestUsers;
import com.applidium.graphql.client.data.net.retrofit.model.RestUsersMapper;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class ServiceRestRepository implements RestRepository {
    private final GraphQL_AndroidService service;
    private final RestUsersMapper mapper;

    @Inject ServiceRestRepository(GraphQL_AndroidService service, RestUsersMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public List<User> getListOfUsers() throws IOException {
        Call<List<RestUsers>> userses = service.getListOfUsers();
        List<RestUsers> response = userses.execute().body();
        return mapper.mapList(response);
    }
}
