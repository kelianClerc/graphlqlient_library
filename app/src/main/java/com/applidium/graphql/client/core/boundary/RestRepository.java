package com.applidium.graphql.client.core.boundary;

import com.applidium.graphql.client.core.entity.User;

import java.io.IOException;
import java.util.List;

public interface RestRepository {
    List<User> getListOfUsers() throws IOException;
}
