package com.applidium.graphql.client.core.interactor.listofusers;

import com.applidium.graphql.client.core.boundary.RestRepository;
import com.applidium.graphql.client.core.boundary.UserRepository;
import com.applidium.graphql.client.core.entity.User;
import com.applidium.graphql.client.utils.threading.RunOnExecutionThread;
import com.applidium.graphql.client.utils.threading.RunOnPostExecutionThread;
import com.applidium.graphql.client.utils.trace.Trace;
import com.applidium.graphqlient.exceptions.QLException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GetListOfUsersInteractor {
    private GetListUsersListener listener;
    private boolean isQLRequest;
    private final UserRepository userRepository;
    private final RestRepository restRepository;

    @Inject GetListOfUsersInteractor(UserRepository userRepository, RestRepository restRepository) {
        this.userRepository = userRepository;
        this.restRepository = restRepository;
    }


    @Trace @RunOnExecutionThread
    public void execute(GetListUsersListener listener, boolean isQLRequest) {
        this.listener = listener;
        this.isQLRequest = isQLRequest;

        tryToGetListOfUsers();
    }

    private void tryToGetListOfUsers() {
        try {
            getListOfUsers();
        } catch (QLException e) {
            handleError(e.getMessage());
        } catch (IOException e1) {
            e1.printStackTrace();
            handleError(e1.getMessage());
        }
    }

    private void getListOfUsers() throws QLException, IOException {
        List<User> users;
        if (isQLRequest) {
            users = userRepository.getListOfUsers();
        } else {
            users = restRepository.getListOfUsers();
        }
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
