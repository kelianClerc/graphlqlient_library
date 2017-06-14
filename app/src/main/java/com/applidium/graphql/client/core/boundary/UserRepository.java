package com.applidium.graphql.client.core.boundary;

import com.applidium.graphql.client.core.entity.User;
import com.applidium.graphqlient.exceptions.QLException;

import java.util.List;

public interface UserRepository {
    List<User> getListOfUsers() throws QLException;
}
