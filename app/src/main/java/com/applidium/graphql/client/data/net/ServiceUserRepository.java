package com.applidium.graphql.client.data.net;

import com.applidium.graphql.client.UserListRequest;
import com.applidium.graphql.client.UserListResponse;
import com.applidium.graphql.client.UserPostsRequest;
import com.applidium.graphql.client.UserPostsResponse;
import com.applidium.graphql.client.VoteMutation;
import com.applidium.graphql.client.VoteResponse;
import com.applidium.graphql.client.core.boundary.UserRepository;
import com.applidium.graphql.client.core.entity.Posts;
import com.applidium.graphql.client.core.entity.User;
import com.applidium.graphql.client.data.net.graphql.mapper.ListUserMapper;
import com.applidium.graphql.client.data.net.graphql.mapper.UserPostMapper;
import com.applidium.graphql.client.data.net.graphql.mapper.VoteQLMapper;
import com.applidium.graphqlient.GraphQL;
import com.applidium.graphqlient.exceptions.QLException;

import java.util.List;

import javax.inject.Inject;

public class ServiceUserRepository implements UserRepository {

    private final GraphQL graphql;
    private final ListUserMapper mapper;
    private final UserPostMapper userPostMapper;
    private final VoteQLMapper postMapper;

    @Inject ServiceUserRepository(ListUserMapper mapper, UserPostMapper userPostMapper,
                                  VoteQLMapper postMapper) {
        this.mapper = mapper;
        this.userPostMapper = userPostMapper;
        this.postMapper = postMapper;
        graphql = new GraphQL("http://localhost:3000/graphql/test");
    }

    @Override
    public List<User> getListOfUsers() throws QLException {
        UserListRequest request = new UserListRequest();
        UserListResponse response = (UserListResponse) graphql.send(request).getResponses(); //
        // TODO (kelianclerc) 14/6/17 to simplify
        return mapper.mapList(response);
    }

    @Override
    public User getUserPosts(String targetId) throws QLException {
        UserPostsRequest request = new UserPostsRequest(targetId);
        UserPostsResponse response = (UserPostsResponse) graphql.send(request).getResponses();
        return userPostMapper.map(response.user());
    }

    @Override
    public Posts updateVoteCounts(String targetId) throws QLException {
        VoteMutation mutation = new VoteMutation(targetId);
        VoteResponse response = (VoteResponse) graphql.send(mutation).getResponses();
        return postMapper.map(response.AddVoteToPost());
    }
}
