package com.applidium.graphql.client.core.interactor.userposts;

import com.applidium.graphql.client.core.boundary.UserRepository;
import com.applidium.graphql.client.core.entity.User;
import com.applidium.graphql.client.utils.threading.RunOnExecutionThread;
import com.applidium.graphql.client.utils.threading.RunOnPostExecutionThread;
import com.applidium.graphql.client.utils.trace.Trace;
import com.applidium.graphqlient.exceptions.QLException;

import javax.inject.Inject;

public class GetUserPostsInteractor {
    private String idTarget;
    private GetUserPostsListener listener;
    private final UserRepository userRepository;

    @Inject
    GetUserPostsInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Trace
    @RunOnExecutionThread
    public void execute(String idTarget, GetUserPostsListener listener) {
        this.idTarget = idTarget;
        this.listener = listener;
        tryToGetUserPosts();
    }

    private void tryToGetUserPosts() {
        try {
            getUserPosts();
        } catch (QLException e) {
            handleError(e.getMessage());
        }
    }

    private void getUserPosts() throws QLException {
        User user = userRepository.getUserPosts(idTarget);
        UserPostsResponse response = makeResponse(user);
        handleSuccess(response);
    }

    private UserPostsResponse makeResponse(User users) {
        return new UserPostsResponseBuilder().userResponse(users).build();
    }

    @RunOnPostExecutionThread
    private void handleSuccess(UserPostsResponse response) {
        if (listener != null) {
            listener.onGetUserPostsSuccess(response);
        }
    }

    @RunOnPostExecutionThread
    private void handleError(String errorMessage) {
        if (listener != null) {
            listener.onGetUserPostsError(errorMessage);
        }
    }
}
