package com.applidium.graphql.client.core.interactor.listofusers;

import com.applidium.graphql.client.core.boundary.UserRepository;
import com.applidium.graphql.client.core.entity.User;
import com.applidium.graphql.client.utils.threading.RunOnExecutionThread;
import com.applidium.graphql.client.utils.threading.RunOnPostExecutionThread;
import com.applidium.graphql.client.utils.trace.Trace;
import com.applidium.graphqlient.exceptions.QLException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GetListOfUsersInteractor {
    private GetListUsersListener listener;
    private final UserRepository userRepository;

    @Inject GetListOfUsersInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Trace @RunOnExecutionThread
    public void execute(GetListUsersListener listener) {
        this.listener = listener;
        tryToGetListOfUsers();
    }

    private void tryToGetListOfUsers() {
        try {
            getListOfUsers();
        } catch (QLException e) {
            handleError(e.getMessage());
        }
    }

    private void getListOfUsers() throws QLException {
        List<User> users = userRepository.getListOfUsers();
        List<ListUsersResponse> response = makeResponse(users);
        handleSuccess(response);
    }

    private List<ListUsersResponse> makeResponse(List<User> users) {
        List<ListUsersResponse> result = new ArrayList<>();
        for (User user : users) {
            result.add(new ListUsersResponseBuilder()
                .name(user.name())
                .email(user.email())
                .id(user.id())
                .numberOfPosts(user.numberOfPosts())
                .build());
        }

        return result;
    }

    @RunOnPostExecutionThread
    private void handleSuccess(List<ListUsersResponse> response) {
        if (listener != null) {
            listener.onGetListOfUsersSuccess(response);
        }
    }

    @RunOnPostExecutionThread
    private void handleError(String errorMessage) {
        if (listener != null) {
            listener.onGetListOfUsersError(errorMessage);
        }
    }
}
