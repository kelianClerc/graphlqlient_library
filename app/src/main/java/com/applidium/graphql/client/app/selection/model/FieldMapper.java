package com.applidium.graphql.client.app.selection.model;

import com.applidium.graphql.client.utils.mapper.Mapper;
import com.applidium.graphql.client.utils.mapper.MapperHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class FieldMapper implements Mapper<Member,FieldViewModel> {

    private final MapperHelper mapperHelper;

    @Inject
    FieldMapper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }

    @Override
    public FieldViewModel map(Member toMap) {
        String name;
        Type type;

        name = toMap.getName();
        type = computeType(toMap);

        return new FieldViewModelBuilder().fieldName(name).type(type).isChecked(false).build();
    }

    private Type computeType(Member toMap) {
        if (toMap instanceof Field) {
            return ((Field) toMap).getGenericType();
        }
        if (toMap instanceof Method) {
            return ((Method) toMap).getGenericReturnType();
        }
        return null;
    }

    public List<FieldViewModel> mapList(List<Member> data) {
        return mapperHelper.mapList(data, this);
    }
}
