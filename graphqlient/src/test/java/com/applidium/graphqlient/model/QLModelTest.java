package com.applidium.graphqlient.model;

import com.applidium.graphqlient.annotations.Alias;
import com.applidium.graphqlient.annotations.Argument;
import com.applidium.graphqlient.annotations.Parameters;
import com.applidium.graphqlient.tree.QLElement;
import com.applidium.graphqlient.tree.QLLeaf;
import com.applidium.graphqlient.tree.QLNode;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.applidium.graphqlient.model.QLModelTest.TestEnum.A;
import static com.applidium.graphqlient.model.QLModelTest.TestEnum.B;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class QLModelTest {

    private class StandardTypeSample {
        String a = "dfs";
        @Alias(name = "A string")
        int b = 1;
        @Parameters(table = {
            @Argument(argumentName = "id", argumentValue = "1"),
            @Argument(argumentName = "id2", argumentValue = "2"),
            @Argument(argumentName = "id3", argumentValue = "3"),
        })
        int b2;
        Integer c = 2;
        long d = 3;
        Long e = Long.valueOf(3);
        boolean f = true;
        Boolean g = false;
        float h = 1f;
        Float i = 2f;
        TestEnum j = A;
        TestEnum k = B;
        QLModel test;
        Object o;
        List<String> stringList;
    }

    private class Sample2 {
        int aa;
        StandardTypeSample bb;
        List<StandardTypeSample> cc;
    }

    private QLModel qlModel;

    @Before
    public void setUp() throws Exception {
        qlModel = new QLModel();
    }

    @Test
    public void standardTypeTest() throws Exception {
        assertEqualsType("a", QLLeaf.class);
        assertEqualsType("b", QLLeaf.class);
        assertEqualsType("b2", QLLeaf.class);
        assertEqualsType("c", QLLeaf.class);
        assertEqualsType("d", null);
        assertEqualsType("e", null);
        assertEqualsType("f", QLLeaf.class);
        assertEqualsType("g", QLLeaf.class);
        assertEqualsType("h", QLLeaf.class);
        assertEqualsType("i", QLLeaf.class);
        assertEqualsType("j", QLLeaf.class);
        assertEqualsType("k", QLLeaf.class);
        assertEqualsType("test", QLNode.class);
        assertEqualsType("o", null);
        assertEqualsType("stringList", QLLeaf.class);
    }

    private void assertEqualsType(String field, Class<?> shouldBe) throws NoSuchFieldException {
        List<QLElement> elements = new ArrayList<>();
        qlModel.appendQLElement(elements, StandardTypeSample.class.getDeclaredField(field));
        if (shouldBe != null) {
            assertThat(elements.get(0), instanceOf(shouldBe));
        } else {
            assertTrue(elements.size() == 0);
        }
    }

    enum TestEnum {
        A, B, C, D;
    }

    @Test
    public void createElementTest() throws Exception {
        Field field = StandardTypeSample.class.getDeclaredField("a");
        List<QLElement> elements = new ArrayList<>();
        qlModel.appendQLElement(elements, field);
        QLElement element = elements.get(0);
        assertTrue(element.getName().equals("a"));
        assertTrue(element.getAlias() == null);
        assertTrue(element.getParameters().size() == 0);


        Field field2 = StandardTypeSample.class.getDeclaredField("b");
        elements.clear();
        qlModel.appendQLElement(elements, field2);
        QLElement element1 = elements.get(0);
        assertTrue(element1.getName().equals("b"));
        assertTrue(element1.getAlias().equals("A string"));
        assertTrue(element1.getParameters().size() == 0);

        Field field3 = StandardTypeSample.class.getDeclaredField("b2");
        elements.clear();
        qlModel.appendQLElement(elements, field3);
        QLElement element3 = elements.get(0);
        assertTrue(element3.getName().equals("b2"));
        assertTrue(element3.getAlias() == null);
        assertTrue(element3.getParameters().size() == 3);
        assertTrue(element3.getParameters().get("id").equals("1"));
        assertTrue(element3.getParameters().get("id2").equals("2"));
        assertTrue(element3.getParameters().get("id3").equals("3"));
    }

    @Test
    public void createLeafElement() throws Exception {
        Field field = StandardTypeSample.class.getDeclaredField("a");
        List<QLElement> elements = new ArrayList<>();
        qlModel.appendQLElement(elements, field);
        QLElement element = elements.get(0);
        assertTrue(element instanceof QLLeaf);
    }

    @Test
    public void createNodeElementTest() throws Exception {
        Field field = Sample2.class.getDeclaredField("bb");
        QLElement element = qlModel.createNodeFromField(field);
        assertTrue(element instanceof QLNode);
        assertTrue(element.getName().equals("bb"));
        assertTrue(((QLNode) element).getChildren().size() == 12);

        Field field2 = Sample2.class.getDeclaredField("cc");
        QLElement element2 = qlModel.createNodeFromField(field2);
        assertTrue(element2 instanceof QLNode);
        assertTrue(element2.getName().equals("cc"));
        assertTrue(((QLNode) element2).getChildren().size() == 12);
    }
}
