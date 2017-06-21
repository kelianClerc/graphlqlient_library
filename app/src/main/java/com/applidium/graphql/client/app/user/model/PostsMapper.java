package com.applidium.graphql.client.app.user.model;

import com.applidium.graphql.client.core.entity.Posts;
import com.applidium.graphql.client.utils.mapper.Mapper;
import com.applidium.graphql.client.utils.mapper.MapperHelper;

import org.joda.time.format.DateTimeFormat;

import java.util.List;

import javax.inject.Inject;

public class PostsMapper implements Mapper<Posts, PostViewModel> {

    private final MapperHelper mapperHelper;

    @Inject PostsMapper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }

    public List<PostViewModel> mapList(List<Posts> toMap) {
        return mapperHelper.mapList(toMap, this);
    }

    @Override
    public PostViewModel map(Posts toMap) {
        return new PostViewModelBuilder()
            .id(toMap.id())
            .author(toMap.authorName())
            .title(toMap.title())
            .body(toMap.body())
            .numberOfComments(toMap.commentsCount())
            .votesCount(toMap.votesCount())
            .creationDate(DateTimeFormat.mediumDate().print(toMap.createdAt()))
            .build();
    }
}
