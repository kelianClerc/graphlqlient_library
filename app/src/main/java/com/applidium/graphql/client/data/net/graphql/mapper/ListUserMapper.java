package com.applidium.graphql.client.data.net.graphql.mapper;

import com.applidium.graphql.client.UserListResponse;
import com.applidium.graphql.client.core.entity.User;

import java.util.List;

import javax.inject.Inject;

public class ListUserMapper {

    @Inject ListUserMapper() {
    }

    public List<User> map(UserListResponse response) {
        // TODO (kelianclerc) 14/6/17
        return null;
    }
}
