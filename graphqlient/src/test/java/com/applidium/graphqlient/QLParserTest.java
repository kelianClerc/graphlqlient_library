package com.applidium.graphqlient;

import com.applidium.graphqlient.tree.QLElement;
import com.applidium.graphqlient.tree.QLLeaf;
import com.applidium.graphqlient.tree.QLNode;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class QLParserTest {

    @Test
    public void initClass() throws Exception {
        QLParser parser = new QLParser();
        assertEquals(parser.begin(), null);

        QLParser parser2 = new QLParser("string");
        assertEquals(parser2.begin(), null);
    }

    @Test
    public void checkQueryHeader() throws Exception {
        QLParser parser = new QLParser();

        parser.setToParse("query hello {}");
        QLQuery query = parser.begin();
        assertEquals(query.name, "hello");

        QLParser parser2 = new QLParser();
        parser2.setToParse("{}");
        QLQuery response2 = parser2.begin();
        assertEquals(response2.name, null);
    }

    @Test
    public void checkEndpoint() throws Exception {
        QLParser parser = new QLParser();

        parser.setToParse("query hello {user {}}");
        QLQuery query = parser.begin();
        assertEquals(query.name, "hello");
        assertEquals(query.getQueryFields().size(), 1);
        assertEquals(query.getQueryFields().get(0).getName(), "user");


        parser.setToParse("query hello {test : user {}}");
        QLQuery query2 = parser.begin();
        assertEquals(query2.name, "hello");
        assertEquals(query2.getQueryFields().size(), 1);
        assertEquals(query2.getQueryFields().get(0).getName(), "user");
        assertEquals(query2.getQueryFields().get(0).getAlias(), "test");

        parser.setToParse("query hello {test : user(id:12f) {}");
        QLQuery query3 = parser.begin();
        assertEquals(query3.name, "hello");
        assertEquals(query3.getQueryFields().size(), 1);
        assertEquals(query3.getQueryFields().get(0).getName(), "user");
        assertEquals(query3.getQueryFields().get(0).getAlias(), "test");
        assertEquals(query3.getQueryFields().get(0).getParameters().size(), 1);
        assertTrue(query3.getQueryFields().get(0).getParameters().containsKey("id"));
        assertEquals(query3.getQueryFields().get(0).getParameters().get("id"), "12f");
    }

    @Test
    public void checkFields() throws Exception {
        QLParser parser = new QLParser();

        parser.setToParse("query hello {user {aa, ab:bb, cc(aze: a), abc:dd(azerr:b, zef:c)} bib{dsf}}");
        QLQuery query = parser.begin();
        assertEquals(query.name, "hello");
        assertEquals(query.getQueryFields().size(), 2);
        assertThat(query.getQueryFields().get(0), instanceOf(QLNode.class));
        List<QLElement> children = query.getQueryFields().get(0).getChildren();
        assertEquals(children.size(), 4);
        QLElement firstChild = children.get(0);
        QLElement secondChild = children.get(1);
        QLElement thirdChild = children.get(2);
        QLElement fourthChild = children.get(3);

        assertEquals(firstChild.getName(), "aa");
        assertThat(firstChild, instanceOf(QLLeaf.class));

        assertEquals(secondChild.getName(), "bb");
        assertThat(secondChild, instanceOf(QLLeaf.class));
        assertEquals(secondChild.getAlias(), "ab");

        assertEquals(thirdChild.getName(), "cc");
        assertEquals(thirdChild.getParameters().size(), 1);
        assertTrue(thirdChild.getParameters().containsKey("aze"));
        assertEquals(thirdChild.getParameters().get("aze"), "a");
        assertThat(thirdChild, instanceOf(QLLeaf.class));

        assertEquals(fourthChild.getName(), "dd");
        assertEquals(fourthChild.getAlias(), "abc");
        assertEquals(fourthChild.getParameters().size(), 2);
        assertTrue(fourthChild.getParameters().containsKey("azerr"));
        assertTrue(fourthChild.getParameters().containsKey("zef"));
        assertEquals(fourthChild.getParameters().get("zef"), "c");
        assertEquals(fourthChild.getParameters().get("azerr"), "b");
        assertThat(fourthChild, instanceOf(QLLeaf.class));
        
        assertThat(query.getQueryFields().get(1), instanceOf(QLNode.class));
        assertEquals(query.getQueryFields().get(1).getName(), "bib");
        assertEquals(query.getQueryFields().get(1).getChildren().size(), 1);
        assertEquals(query.getQueryFields().get(1).getChildren().get(0).getName(), "dsf");
    }

    @Test
    public void checkObjectAsField() throws Exception {
        QLParser parser = new QLParser();

        parser.setToParse("query hello {user {aa, bb{cc,dd(id:v, vf:d){ee}}}}");
        QLQuery query = parser.begin();
        assertEquals(query.name, "hello");
        assertEquals(query.getQueryFields().size(), 1);
        List<QLElement> children = query.getQueryFields().get(0).getChildren();
        assertEquals(children.size(), 2);
        assertEquals(children.get(0).getName(), "aa");
        assertThat(children.get(0), instanceOf(QLLeaf.class));
        assertThat(children.get(1), instanceOf(QLNode.class));
        QLNode element = (QLNode) children.get(1);
        assertEquals(element.getName(), "bb");
        assertEquals(element.getChildren().size(), 2);
        assertEquals(element.getChildren().get(0).getName(), "cc");

        assertThat(element.getChildren().get(1), instanceOf(QLNode.class));
        QLNode childNode = (QLNode) element.getChildren().get(1);
        assertEquals(childNode.getName(), "dd");
        assertEquals(childNode.getParameters().size(), 2);
        assertEquals(childNode.getParameters().get("id"), "v");
        assertEquals(childNode.getParameters().get("vf"), "d");
        assertEquals(childNode.getChildren().size(), 1);
        assertEquals(childNode.getChildren().get(0).getName(), "ee");
    }

    @Test
    public void checkFullAnonymousQuery() throws Exception {
        QLParser parser = new QLParser();
        parser.setToParse("{user {aa, bb{cc,dd(id:v, vf:d){ee}}}}");
        QLQuery query = parser.begin();
        assertEquals(query.name, null);
        assertEquals(query.getQueryFields().size(), 1);
        List<QLElement> children = query.getQueryFields().get(0).getChildren();
        assertEquals(children.size(), 2);
        assertEquals(children.get(0).getName(), "aa");
        assertThat(children.get(0), instanceOf(QLLeaf.class));
        assertThat(children.get(1), instanceOf(QLNode.class));
        QLNode element = (QLNode) children.get(1);
        assertEquals(element.getName(), "bb");
        assertEquals(element.getChildren().size(), 2);
        assertEquals(element.getChildren().get(0).getName(), "cc");

        assertThat(element.getChildren().get(1), instanceOf(QLNode.class));
        QLNode childNode = (QLNode) element.getChildren().get(1);
        assertEquals(childNode.getName(), "dd");
        assertEquals(childNode.getParameters().size(), 2);
        assertEquals(childNode.getParameters().get("id"), "v");
        assertEquals(childNode.getParameters().get("vf"), "d");
        assertEquals(childNode.getChildren().size(), 1);
        assertEquals(childNode.getChildren().get(0).getName(), "ee");

    }
}
