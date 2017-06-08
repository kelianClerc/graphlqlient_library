package com.applidium.graphql.client.app.selection.model;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class ModelMapper {

    private final FieldMapper mapper;

    @Inject
    ModelMapper(FieldMapper mapper) {
        this.mapper = mapper;
    }

    public ModelViewModel map(Class<?> model) {
        return new ModelViewModelBuilder()
            .objectName(model.getName())
            .fields(mapper.mapList(getData(model)))
            .build();
    }

    private List<Member> getData(Class<?> model) {
        if (model.getDeclaredFields().length < 1) {
            return new ArrayList<Member>(Arrays.asList(model.getDeclaredMethods()));
        }
        return new ArrayList<Member>(Arrays.asList(model.getDeclaredFields()));
    }
}
