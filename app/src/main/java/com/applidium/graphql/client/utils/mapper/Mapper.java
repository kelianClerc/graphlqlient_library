package com.applidium.graphql.client.utils.mapper;

public interface Mapper<U, T> {
    T map(U toMap);
}
