package com.applidium.graphqlient;

import com.applidium.graphqlient.exceptions.QLException;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.fail;

public class GraphQLTest {
    @Test
    public void callGlobalTest() throws Exception {
        GraphQL graphQL = new GraphQL("http://localhost:8080/");
        QLQuery qlQuery = graphQL.buildQuery("query hello($try: Boolean!, $try2:String, $try3:Int, $try4:Float, $try5:ID!) {user{id}}");
        try {
            graphQL.call(qlQuery);
            fail("QLException should have been thrown");
        } catch (QLException e) {
            assertEquals(e.getMessage(), "Not all mandatory parameters of query \"hello\" are " +
                "provided as QLVariable");
        }
        qlQuery.addVariable("try", 1);
        qlQuery.addVariable("try5", 1);
        assertFalse(graphQL.call(qlQuery) == null);
    }

}
