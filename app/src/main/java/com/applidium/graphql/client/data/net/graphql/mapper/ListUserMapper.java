package com.applidium.graphql.client.data.net.graphql.mapper;

import com.applidium.graphql.client.UserListResponse;
import com.applidium.graphql.client.core.entity.User;
import com.applidium.graphql.client.core.entity.UserBuilder;
import com.applidium.graphql.client.utils.mapper.Mapper;
import com.applidium.graphql.client.utils.mapper.MapperHelper;

import org.joda.time.DateTime;

import java.util.List;

import javax.inject.Inject;

public class ListUserMapper implements Mapper<UserListResponse.Users, User> {

    private final MapperHelper mapperHelper;

    @Inject ListUserMapper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }

    public List<User> mapList(UserListResponse toMap) {
        return mapperHelper.mapList(toMap.users(), this);
    }

    @Override
    public User map(UserListResponse.Users toMap) {
        return new UserBuilder()
            .name(toMap.name())
            .email(toMap.email())
            .id(toMap.id())
            .createdAt(DateTime.now())
            .numberOfPosts(toMap.numberOfPosts())
            .build();
    }
}
