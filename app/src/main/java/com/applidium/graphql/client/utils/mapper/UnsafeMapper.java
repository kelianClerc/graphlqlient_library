package com.applidium.graphql.client.utils.mapper;

import com.applidium.graphql.client.core.error.exceptions.MappingException;

public interface UnsafeMapper<U, T> {
    T map(U toMap) throws MappingException;
}
