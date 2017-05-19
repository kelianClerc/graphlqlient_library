package com.applidium.graphqlient.tree;

import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

public class QLElementTest {
    @Test
    public void elementOnlyNamed() throws Exception {
        String name = "name";
        QLElement element = new QLElement(name);
        assertEquals(element.print(), name);
    }

    @Test
    public void elementWithAlias() throws Exception {
        String name = "name";
        String alias = "alias";
        QLElement element = new QLElement(name, alias);
        assertEquals(element.print(), alias + ":" + name);

    }

    @Test
    public void elementWithParams() throws Exception {
        String name = "name";
        String key = "id";
        String key2 = "id2";
        int value = 1;
        int value2 = 2;
        HashMap<String, Object> params = new HashMap<>();
        params.put(key, value);
        QLElement element = new QLElement(name, params);
        assertEquals(element.print(), name + "(" + key + ":" + value +")");


        HashMap<String, Object> params2 = new HashMap<>();
        params2.putAll(params);
        params2.put(key2, value2);
        QLElement element2 = new QLElement(name, params2);
        assertEquals(element2.print(), name + "(" + key2 + ":" + value2 +"," + key + ":" + value + ")");

    }

    @Test
    public void elementWithParamsAndAlias() throws Exception {
        String name = "name";
        String alias = "alias";
        String key = "id";
        int value = 1;
        HashMap<String, Object> params = new HashMap<>();
        params.put(key, value);
        QLElement element = new QLElement(name, alias, params);
        assertEquals(element.print(), alias + ":" + name + "(" + key + ":" + value +")");
    }
}
