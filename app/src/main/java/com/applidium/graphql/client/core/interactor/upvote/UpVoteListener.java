package com.applidium.graphql.client.core.interactor.upvote;

public interface UpVoteListener {
    void onGetUpVoteSuccess(UpVoteResponse response);
    void onGetUpVoteError(String message);
}
