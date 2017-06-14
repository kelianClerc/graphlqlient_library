package com.applidium.graphql.client.core.interactor.listofusers;

import com.applidium.graphql.client.core.boundary.UserRepository;
import com.applidium.graphql.client.core.entity.User;
import com.applidium.graphql.client.utils.threading.RunOnExecutionThread;
import com.applidium.graphql.client.utils.threading.RunOnPostExecutionThread;
import com.applidium.graphql.client.utils.trace.Trace;
import com.applidium.graphqlient.exceptions.QLException;

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
        ListUsersResponse response = makeResponse(users);
        handleSuccess(response);
    }

    private ListUsersResponse makeResponse(List<User> users) {
        return null;
    }

    @RunOnPostExecutionThread
    private void handleSuccess(ListUsersResponse response) {
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
