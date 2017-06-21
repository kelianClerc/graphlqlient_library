package com.applidium.graphql.client.core.interactor.upvote;

import com.applidium.graphql.client.core.entity.Posts;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface UpVoteResponse {
    Posts post();
}
