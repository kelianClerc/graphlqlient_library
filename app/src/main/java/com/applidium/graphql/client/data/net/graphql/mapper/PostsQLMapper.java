package com.applidium.graphql.client.data.net.graphql.mapper;

import com.applidium.graphql.client.UserPostsResponse;
import com.applidium.graphql.client.core.entity.Posts;
import com.applidium.graphql.client.core.entity.PostsBuilder;
import com.applidium.graphql.client.utils.mapper.Mapper;
import com.applidium.graphql.client.utils.mapper.MapperHelper;

import org.joda.time.DateTime;

import java.util.List;

import javax.inject.Inject;

public class PostsQLMapper implements Mapper<UserPostsResponse.User.Posts, Posts> {

    private final MapperHelper mapperHelper;

    @Inject PostsQLMapper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }

    @Override
    public Posts map(UserPostsResponse.User.Posts toMap) {
        return new PostsBuilder()
            .id(toMap.id())
            .title(toMap.title())
            .body(toMap.body())
            .createdAt(DateTime.parse(toMap.created_at().split(" ")[0]))
            .authorName(toMap.user().name())
            .commentsCount(Integer.valueOf(toMap.comments_count()))
            .votesCount(Integer.valueOf(toMap.votes_count()))
            .build();
    }

    public List<Posts> mapList(List<UserPostsResponse.User.Posts> posts) {
        return mapperHelper.mapList(posts, this);
    }
}
