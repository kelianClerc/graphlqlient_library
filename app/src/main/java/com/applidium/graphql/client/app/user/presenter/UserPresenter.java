package com.applidium.graphql.client.app.user.presenter;

import com.applidium.graphql.client.app.common.Presenter;
import com.applidium.graphql.client.app.user.model.PostViewModel;
import com.applidium.graphql.client.app.user.model.PostViewModelBuilder;
import com.applidium.graphql.client.app.user.model.UserMapper;
import com.applidium.graphql.client.app.user.model.UserViewModel;
import com.applidium.graphql.client.app.user.ui.UserViewContract;
import com.applidium.graphql.client.core.entity.Posts;
import com.applidium.graphql.client.core.entity.PostsBuilder;
import com.applidium.graphql.client.core.interactor.upvote.UpVoteInteractor;
import com.applidium.graphql.client.core.interactor.upvote.UpVoteListener;
import com.applidium.graphql.client.core.interactor.upvote.UpVoteResponse;
import com.applidium.graphql.client.core.interactor.upvote.UpVoteResponseBuilder;
import com.applidium.graphql.client.core.interactor.userposts.GetUserPostsInteractor;
import com.applidium.graphql.client.core.interactor.userposts.GetUserPostsListener;
import com.applidium.graphql.client.core.interactor.userposts.UserPostsResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UserPresenter extends Presenter<UserViewContract> implements GetUserPostsListener, UpVoteListener {
    private final GetUserPostsInteractor interactor;
    private final UpVoteInteractor interactorVote;
    private final UserMapper mapper;
    private com.applidium.graphql.client.app.user.model.UserViewModel userViewModel;

    @Inject UserPresenter(
        UserViewContract view, GetUserPostsInteractor interactor, UpVoteInteractor interactorVote, UserMapper
        mapper) {
        super(view);
        this.interactor = interactor;
        this.interactorVote = interactorVote;
        this.mapper = mapper;
    }

    public void onStart(String idTarget) {
        interactor.execute(idTarget, this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onGetUserPostsSuccess(UserPostsResponse response) {
        UserViewModel userViewModel = mapper.map(response.userResponse());
        this.userViewModel = userViewModel;
        view.showUserProfile(userViewModel);
    }

    @Override
    public void onGetUserPostsError(String errorMessage) {
        view.showError(errorMessage);
    }

    public void onVote(PostViewModel postViewModel) {
        interactorVote.execute(postViewModel.id(), this);
        Posts postsBuilder = new PostsBuilder().id(postViewModel.id()).votesCount(postViewModel.votesCount() + 1).build();
        UpVoteResponse response = new UpVoteResponseBuilder().post(postsBuilder).build();
        updateUserViewModel(response);
    }

    @Override
    public void onGetUpVoteSuccess(UpVoteResponse response) {
        updateUserViewModel(response);
        view.showUserProfile(userViewModel);
    }

    private void updateUserViewModel(UpVoteResponse response) {
        List<PostViewModel> posts = new ArrayList<>();
        for (PostViewModel post : userViewModel.posts()) {
            if (post.id() != response.post().id()) {
                posts.add(post);
            } else {
                posts.add(PostViewModelBuilder.from(post).votesCount(response.post().votesCount()).build());
            }
        }

        this.userViewModel = com.applidium.graphql.client.app.user.model.UserViewModelBuilder
            .from(userViewModel)
            .posts(posts)
            .build();
    }

    @Override
    public void onGetUpVoteError(String message) {

    }
}
