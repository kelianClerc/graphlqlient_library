package com.applidium.graphqlient;

import com.applidium.graphqlient.annotations.Alias;
import com.applidium.graphqlient.annotations.Argument;
import com.applidium.graphqlient.annotations.Parameters;
import com.applidium.graphqlient.model.QLModel;
import com.applidium.graphqlient.tree.QLElement;
import com.applidium.graphqlient.tree.QLLeaf;
import com.applidium.graphqlient.tree.QLNode;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class QLQueryTest {

    private QLNode rootNode;

    @Before
    public void setUp() throws Exception {
        QLNode rootNode = new QLNode("1");
        QLLeaf firstChild = new QLLeaf("2");
        QLNode secondChild = new QLNode("3");
        QLLeaf thridChild = new QLLeaf("4");

        secondChild.addChild(thridChild);
        rootNode.addChild(firstChild);
        rootNode.addChild(secondChild);
        this.rootNode = rootNode;
    }

    @Test public void treeConstruction() {
        QLQuery query = new QLQuery();
        query.append(rootNode);

        List<QLNode> getRootElement = query.getQueryFields();
        assertEquals(1, 1);
        assertEquals(getRootElement.size(), 1);

        assertEquals(getRootElement.get(0).getElementInfo(), "1");
        List<QLElement> rootChildren = getRootElement.get(0).getChildren();
        assertEquals(rootChildren.size(), 2);
        assertEquals(rootChildren.get(0).print(), "2");
        assertThat(rootChildren.get(1), instanceOf(QLNode.class));
        QLNode node = (QLNode) rootChildren.get(1);
        assertEquals(node.getElementInfo(), "3");
        List<QLElement> secondeChildren = node.getChildren();
        assertEquals(secondeChildren.size(), 1);
        assertEquals(secondeChildren.get(0).print(), "4");
    }

    @Test public void treePrint() {
        QLQuery query = new QLQuery();
        query.append(rootNode);
        assertEquals(query.printQuery(), "{1{2,3{4}}}");
    }

    @Test public void treePrintWithName() {
        String aRandomName = "aRandomName";
        QLQuery query = new QLQuery(aRandomName);
        query.append(rootNode);
        assertEquals(query.printQuery(), "query " + aRandomName +"{1{2,3{4}}}");
    }

    @Test
    public void queryParamsTest() throws Exception {

        String aRandomName = "aRandomName";
        String aRandomName2 = "aRandomName2";
        QLQuery query = new QLQuery(aRandomName);
        QLVariablesElement param = new QLVariablesElement("test", QLType.STRING, true);
        QLVariablesElement param2 = new QLVariablesElement("test2", QLType.INT);
        query.setParameters(Arrays.asList(param, param2));
        query.append(rootNode);

        assertEquals(query.printQuery(), "query " + aRandomName +"($test:String!,$test2:Int){1{2,3{4}}}");


        QLQuery query1 = new QLQuery(aRandomName, Arrays.asList(param, param2));
        query1.setQueryFields(Arrays.asList(rootNode));
        query1.setName(aRandomName2);
        assertEquals(query1.printQuery(), "query " + aRandomName2 +"($test:String!,$test2:Int){1{2,3{4}}}");


        Map<String,Object> map = new HashMap<>();
        map.put("id", new QLVariablesElement("test"));
        QLNode node = new QLNode(rootNode.getElement());
        node.setParameters(map);
        node.setAllChild(rootNode.getChildren());
        QLQuery query2 = new QLQuery(aRandomName, Arrays.asList(param, param2));
        query2.setQueryFields(Arrays.asList(node));
        query2.setName(aRandomName2);
        assertEquals(query2.printQuery(), "query " + aRandomName2 +"($test:String!,$test2:Int){1(id:$test){2,3{4}}}");

    }

    @Test
    public void queryFromObjectTest() throws Exception {
        QLQuery qlQuery = new QLQuery("name");
        QueryTest queryTest = new QueryTest();
        qlQuery.append(queryTest.getUser());
        assertEquals(qlQuery.printQuery(), "query name{user(id:\"1\"){id,name,essai:email,posts{id,title}}}");
    }

    private class QueryTest extends QLModel {
        @Parameters(table={
            @Argument(argumentName = "id", argumentValue = "1")
        })
        private UserTest user;
        private List<UserTest> users;

        public QueryTest() {
        }

        public QLNode getUser() {
            try {
                return createNodeFromField(getClass().getDeclaredField("user"));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class UserTest extends QLModel {
        private String id;
        private String name;
        @Alias(name = "essai") private String email;
        private PostTest posts;
    }

    private class PostTest extends QLModel {
        private String id;
        private String title;
    }
}
