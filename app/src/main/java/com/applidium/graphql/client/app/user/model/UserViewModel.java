package com.applidium.graphql.client.app.user.model;

import java.util.List;

public interface UserViewModel {
    String name();
    String email();
    String memberSince();
    List<PostViewModel> posts();
}
