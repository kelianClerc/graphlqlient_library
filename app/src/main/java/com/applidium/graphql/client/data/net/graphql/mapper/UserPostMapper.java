package com.applidium.graphql.client.data.net.graphql.mapper;

import com.applidium.graphql.client.core.entity.User;
import com.applidium.graphql.client.core.entity.UserBuilder;
import com.applidium.graphql.client.utils.mapper.Mapper;

import org.joda.time.DateTime;

import javax.inject.Inject;

public class UserPostMapper implements Mapper<com.applidium.graphql.client.UserPostsResponse.User, User> {


    private final PostsQLMapper mapper;

    @Inject
    UserPostMapper(PostsQLMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public User map(com.applidium.graphql.client.UserPostsResponse.User toMap) {
        return new UserBuilder()
            .name(toMap.name())
            .email(toMap.email())
            .id(toMap.id())
            .createdAt(DateTime.parse(toMap.created_at().split(" ")[0]))
            .posts(mapper.mapList(toMap.posts()))
            .build();
    }
}
