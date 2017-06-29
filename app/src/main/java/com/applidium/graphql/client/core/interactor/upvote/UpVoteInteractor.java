package com.applidium.graphql.client.core.interactor.upvote;

import com.applidium.graphql.client.core.boundary.UserRepository;
import com.applidium.graphql.client.core.entity.Posts;
import com.applidium.graphql.client.utils.threading.RunOnExecutionThread;
import com.applidium.graphql.client.utils.threading.RunOnPostExecutionThread;
import com.applidium.graphql.client.utils.trace.Trace;
import com.applidium.graphqlient.exceptions.QLException;

import javax.inject.Inject;

public class UpVoteInteractor {
    private String idTarget;
    private UpVoteListener listener;
    private final UserRepository userRepository;

    @Inject
    UpVoteInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Trace
    @RunOnExecutionThread
    public void execute(String idTarget, UpVoteListener listener) {
        this.idTarget = idTarget;
        this.listener = listener;
        tryToUpVote();
    }

    private void tryToUpVote() {
        try {
            upVote();
        } catch (QLException e) {
            handleError(e.getMessage());
        }
    }

    private void upVote() throws QLException {
        Posts post = userRepository.updateVoteCounts(idTarget);
        handleSuccess(makeResponse(post));
    }

    private UpVoteResponse makeResponse(Posts post) {
        return new UpVoteResponseBuilder().post(post).build();
    }

    @RunOnPostExecutionThread
    private void handleSuccess(UpVoteResponse response) {
        if (listener != null) {
            listener.onGetUpVoteSuccess(response);
        }
    }

    @RunOnPostExecutionThread
    private void handleError(String errorMessage) {
        if (listener != null) {
            listener.onGetUpVoteError(errorMessage);
        }
    }
}
