package com.applidium.graphql.client.data.net.retrofit.model;

import com.applidium.graphql.client.core.entity.User;
import com.applidium.graphql.client.core.entity.UserBuilder;
import com.applidium.graphql.client.utils.mapper.Mapper;
import com.applidium.graphql.client.utils.mapper.MapperHelper;

import org.joda.time.DateTime;

import java.util.List;

import javax.inject.Inject;

public class RestUsersMapper implements Mapper<RestUsers, User> {

    private final MapperHelper mapperHelper;

    @Inject RestUsersMapper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }

    public List<User> mapList(List<RestUsers> toMap) {
        return mapperHelper.mapList(toMap, this);
    }

    @Override
    public User map(RestUsers toMap) {
        return new UserBuilder()
            .name(toMap.name())
            .numberOfPosts(toMap.posts().size())
            .email(toMap.email())
            .createdAt(DateTime.parse(toMap.createdAt()))
            .id(toMap.id())
            .build();
    }
}
