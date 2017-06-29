package com.applidium.graphql.client.data.net.graphql.mapper;

import com.applidium.graphql.client.VoteResponse;
import com.applidium.graphql.client.core.entity.Posts;
import com.applidium.graphql.client.core.entity.PostsBuilder;
import com.applidium.graphql.client.utils.mapper.Mapper;
import com.applidium.graphql.client.utils.mapper.MapperHelper;

import java.util.List;

import javax.inject.Inject;

public class VoteQLMapper implements Mapper<VoteResponse.AddVoteToPost, Posts> {

    private final MapperHelper mapperHelper;

    @Inject
    VoteQLMapper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }

    public List<Posts> mapList(List<VoteResponse.AddVoteToPost> posts) {
        return mapperHelper.mapList(posts, this);
    }

    @Override
    public Posts map(VoteResponse.AddVoteToPost toMap) {
        return new PostsBuilder()
            .id(toMap.id())
            .votesCount(Integer.valueOf(toMap.votes_count()))
            .build();
    }
}
