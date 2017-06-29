package com.applidium.graphqlient;

import com.applidium.graphqlient.tree.QLElement;
import com.applidium.graphqlient.tree.QLLeaf;
import com.applidium.graphqlient.tree.QLNode;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
}
