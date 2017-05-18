package com.applidium.graphql.client.utils.mapper;

import com.applidium.graphql.client.core.error.exceptions.MappingException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class MapperHelper {

    @Inject MapperHelper() { }

    public <U, T> List<T> mapList(List<U> toMap, Mapper<U, T> mapper) {
        if (toMap == null) {
            return Collections.emptyList();
        }
        List<T> mapped = new ArrayList<>(toMap.size());
        for (U u : toMap) {
            mapped.add(mapper.map(u));
        }
        return mapped;
    }

    public <U, T> List<T> mapList(
        List<U> toMap, UnsafeMapper<U, T> mapper
    ) throws MappingException {
        if (toMap == null) {
            return Collections.emptyList();
        }
        List<T> mapped = new ArrayList<>(toMap.size());
        for (U u : toMap) {
            mapped.add(mapper.map(u));
        }
        return mapped;
    }
}
