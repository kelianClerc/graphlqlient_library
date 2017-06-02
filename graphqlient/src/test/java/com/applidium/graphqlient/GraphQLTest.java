package com.applidium.graphqlient;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class GraphQLTest {
    @Test
    public void callGlobalTest() throws Exception {
        GraphQL graphQL = new GraphQL("http://localhost:8080/");
        QLQuery qlQuery = graphQL.buildQuery("query hello($try: Boolean!, $try2:String, $try3:Int, $try4:Float, $try5:ID!) {user{id}}");
        assertEquals(graphQL.call(qlQuery), null);
        qlQuery.addVariable("try", 1);
        qlQuery.addVariable("try5", 1);
        assertFalse(graphQL.call(qlQuery) == null);
    }

}
