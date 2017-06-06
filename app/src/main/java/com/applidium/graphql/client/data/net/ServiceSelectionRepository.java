package com.applidium.graphql.client.data.net;

import com.applidium.graphql.client.core.boundary.SelectionRepository;
import com.applidium.graphql.client.utils.trace.Trace;
import com.applidium.graphqlient.QLQuery;
import com.applidium.graphqlient.QLType;
import com.applidium.graphqlient.QLVariablesElement;
import com.applidium.graphqlient.exceptions.QLException;

import java.util.Arrays;

import javax.inject.Inject;

public class ServiceSelectionRepository implements SelectionRepository {

    @Inject ServiceSelectionRepository() {
    }

    @Override @Trace
    public QLQuery createQuery(String queryName, String paramName, String paramType, boolean
        isMandatory) throws QLException {
        QLQuery qlQuery = new QLQuery();
        qlQuery.setName(queryName);
        if (!paramName.isEmpty()) {
            QLVariablesElement element = new QLVariablesElement();
            element.setName(paramName);
            QLType qlType = computeType(paramType);
            element.setType(qlType);
            element.setMandatory(isMandatory);
            qlQuery.setParameters(Arrays.asList(element));
        }
        return qlQuery;
    }

    private QLType computeType(String paramType) {
        switch (paramType) {
            case "Boolean":
                return QLType.BOOLEAN;
            case "String":
                return QLType.STRING;
            case "Integer":
                return QLType.INT;
            case "ID":
                return QLType.ID;
            case "Float":
                return QLType.FLOAT;
            default:
                return QLType.BOOLEAN;
        }
    }
}
