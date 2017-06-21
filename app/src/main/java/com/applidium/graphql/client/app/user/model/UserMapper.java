package com.applidium.graphql.client.app.user.model;

import com.applidium.graphql.client.core.entity.User;
import com.applidium.graphql.client.utils.mapper.Mapper;
import com.applidium.graphql.client.utils.mapper.MapperHelper;

import org.joda.time.format.DateTimeFormat;

import java.util.List;

import javax.inject.Inject;

public class UserMapper implements Mapper<User, UserViewModel> {

    private final MapperHelper mapperHelper;
    private final PostsMapper mapper;

    @Inject UserMapper(MapperHelper mapperHelper, PostsMapper mapper) {
        this.mapperHelper = mapperHelper;
        this.mapper = mapper;
    }

    public List<UserViewModel> mapList(List<User> toMap) {
        return mapperHelper.mapList(toMap, this);
    }

    @Override
    public UserViewModel map(User toMap) {
        return new UserViewModelBuilder()
            .id(toMap.id())
            .email(toMap.email())
            .name(toMap.name())
            .memberSince(DateTimeFormat.mediumDate().print(toMap.createdAt()))
            .posts(mapper.mapList(toMap.posts()))
            .build();
    }
}
