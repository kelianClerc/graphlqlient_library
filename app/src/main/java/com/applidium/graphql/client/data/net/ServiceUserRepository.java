package com.applidium.graphql.client.data.net;

import com.applidium.graphql.client.UserListRequest;
import com.applidium.graphql.client.UserListResponse;
import com.applidium.graphql.client.core.boundary.UserRepository;
import com.applidium.graphql.client.core.entity.User;
import com.applidium.graphql.client.data.net.graphql.mapper.ListUserMapper;
import com.applidium.graphqlient.GraphQL;
import com.applidium.graphqlient.exceptions.QLException;

import java.util.List;

import javax.inject.Inject;

public class ServiceUserRepository implements UserRepository {

    private final GraphQL graphql;
    private final ListUserMapper mapper;

    @Inject ServiceUserRepository(ListUserMapper mapper) {
        this.mapper = mapper;
        graphql = new GraphQL("http://localhost:3000/graphql/test");
    }

    @Override
    public List<User> getListOfUsers() throws QLException {
        UserListRequest request = new UserListRequest();
        UserListResponse response = (UserListResponse) graphql.send(request).getResponses(); //
        // TODO (kelianclerc) 14/6/17 to simplify
        List<User> result = mapper.mapList(response);
        return result;
    }
}
