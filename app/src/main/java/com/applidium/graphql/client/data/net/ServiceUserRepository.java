package com.applidium.graphql.client.data.net;

import com.applidium.graphql.client.ComplexeParamRequest;
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
import com.applidium.graphqlient.call.QLResponse;
import com.applidium.graphqlient.converter.gson.GsonConverterFactory;
import com.applidium.graphqlient.exceptions.QLException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import javax.inject.Inject;

import io.norberg.automatter.gson.AutoMatterTypeAdapterFactory;

public class ServiceUserRepository implements UserRepository {

    private final GraphQL graphql;
    private final ListUserMapper mapper;
    private final UserPostMapper userPostMapper;
    private final VoteQLMapper postMapper;

    @Inject ServiceUserRepository(ListUserMapper mapper, UserPostMapper userPostMapper,
                                  VoteQLMapper postMapper) {

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new AutoMatterTypeAdapterFactory()).create();

        this.mapper = mapper;
        this.userPostMapper = userPostMapper;
        this.postMapper = postMapper;
        GsonConverterFactory converterFactory = GsonConverterFactory.create(gson);
        String url = "http://localhost:3000/graphql/test";
        graphql = new GraphQL.Builder().baseUrl(url).converterFactory(converterFactory).build();
    }

    @Override
    public List<User> getListOfUsers() throws QLException {
        UserListRequest request = new UserListRequest();
        QLResponse<UserListResponse> response = graphql.send(request);

        if (response.getResponse() != null) {
            return mapper.mapList(response.getResponse());
        }
        throw new QLException("Null response");
    }

    @Override
    public User getUserPosts(String targetId) throws QLException {
        UserPostsRequest request = new UserPostsRequest(targetId);
        QLResponse<UserPostsResponse> resp = graphql.send(request);
        return userPostMapper.map(resp.getResponse().user());
    }

    @Override
    public Posts updateVoteCounts(String targetId) throws QLException {
        VoteMutation mutation = new VoteMutation(targetId);
        QLResponse<VoteResponse> response = graphql.send(mutation);
        ComplexeParamRequest request = new ComplexeParamRequest();
        request.User().id("12");
        return postMapper.map(response.getResponse().AddVoteToPost());

    }
}
