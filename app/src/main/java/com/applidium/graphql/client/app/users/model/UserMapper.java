package com.applidium.graphql.client.app.users.model;

import com.applidium.graphql.client.core.interactor.listofusers.ListUsersResponse;
import com.applidium.graphql.client.utils.mapper.Mapper;
import com.applidium.graphql.client.utils.mapper.MapperHelper;

import java.util.List;

import javax.inject.Inject;

public class UserMapper implements Mapper<ListUsersResponse, UserViewModel> {

    private final MapperHelper mapperHelper;

    @Inject UserMapper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }

    public List<UserViewModel> mapList(List<ListUsersResponse> response) {
        return mapperHelper.mapList(response, this);
    }

    @Override
    public UserViewModel map(ListUsersResponse toMap) {
        return new UserViewModelBuilder()
            .id(toMap.id())
            .name(toMap.name())
            .email(toMap.email())
            .numberOfPosts(toMap.numberOfPosts())
            .build();
    }
}
