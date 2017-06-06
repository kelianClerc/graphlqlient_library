package com.applidium.graphql.client.core.boundary;

import com.applidium.graphqlient.QLQuery;
import com.applidium.graphqlient.exceptions.QLException;

public interface SelectionRepository {
    QLQuery createQuery(String queryName, String paramName, String paramType, boolean isMandatory) throws QLException;
}
