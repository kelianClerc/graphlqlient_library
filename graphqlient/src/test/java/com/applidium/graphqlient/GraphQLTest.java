package com.applidium.graphqlient;

import android.support.annotation.Nullable;

import com.applidium.graphqlient.annotations.Alias;
import com.applidium.graphqlient.call.QLCall;
import com.applidium.graphqlient.call.QLResponse;
import com.applidium.graphqlient.exceptions.QLException;
import com.applidium.graphqlient.model.QLModel;
import com.applidium.graphqlient.tree.QLNode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

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

    @Test
    public void parserWithObjectProvidedTest() throws Exception {
        String query = "{user(id:2){name}}";
        GraphQL graphQL = new GraphQL("http://localhost:3000/");
        List<Class<?>> typeList = new ArrayList<>();
        typeList.add(User.class);
        QLQuery qlQuery = graphQL.buildQueryWithTarget(query, typeList);
        assertEquals(qlQuery.getQueryFields().size(), 1);
        QLNode node = qlQuery.getQueryFields().get(0);
        assertEquals(node.getAssociatedObject(), User.class);

        QLCall toCall = graphQL.call(qlQuery);
        QLResponse response = graphQL.send(toCall);
        assertEquals(response.getResponses().size(), 1);
        assertThat(response.getResponses().get("user"), instanceOf(User.class));
        User user = (User) response.getResponses().get("user");
        assertEquals(user.getName(), "Elise Farrell");
        assertEquals(user.getId(), null);
        assertEquals(user.getEmail(), null);
    }

    @Test
    public void parserWithListObjectProvidedTest() throws Exception {
        String query = "{user(id:2){name}users{email, id, name}";
        GraphQL graphQL = new GraphQL("http://localhost:3000/");
        List<Class<?>> typeList = new ArrayList<>();
        typeList.add(User.class);
        typeList.add(User.class);
        QLQuery qlQuery = graphQL.buildQueryWithTarget(query, typeList);
        assertEquals(qlQuery.getQueryFields().size(), 2);
        QLNode node = qlQuery.getQueryFields().get(0);
        assertEquals(node.getAssociatedObject(), User.class);

        QLCall toCall = graphQL.call(qlQuery);
        QLResponse response = graphQL.send(toCall);
        assertEquals(response.getResponses().size(), 2);
        assertThat(response.getResponses().get("user"), instanceOf(User.class));
        User user = (User) response.getResponses().get("user");
        assertEquals(user.getName(), "Elise Farrell");
        assertEquals(user.getId(), null);
        assertEquals(user.getEmail(), null);

        assertThat(response.getResponses().get("users"), instanceOf(List.class));
        List<User> users = (List<User>) response.getResponses().get("users");
        assertEquals(users.get(0).getName(), "Adolph Konopelski");
        assertEquals(users.get(0).getId(), "0");
        assertEquals(users.get(0).getEmail(), "test0@example.com");
    }

    @Test
    public void parseWithSubObjectTest() throws Exception {
        String query = "{posts{title, user{name}}";
        GraphQL graphQL = new GraphQL("http://localhost:3000/");
        List<Class<?>> typeList = new ArrayList<>();
        typeList.add(Post.class);

        QLQuery qlQuery = graphQL.buildQueryWithTarget(query, typeList);
        assertEquals(qlQuery.getQueryFields().size(), 1);
        QLNode node = qlQuery.getQueryFields().get(0);
        assertEquals(node.getAssociatedObject(), Post.class);
        QLCall toCall = graphQL.call(qlQuery);
        QLResponse response = graphQL.send(toCall);
        assertEquals(response.getResponses().size(), 1);

        assertThat(response.getResponses().get("posts"), instanceOf(List.class));
        List<Post> posts = (List<Post>) response.getResponses().get("posts");
        assertEquals(posts.size(), 10);
        Post post = posts.get(0);
        assertEquals(post.getId(), null);
        assertEquals(post.getTitle(), "Perspiciatis optio vitae doloremque.");
        User user = post.getUser();
        assertEquals(user.getName(), "Olin Bogisich");
        assertEquals(user.getId(), null);
        assertEquals(user.getEmail(), null);
    }

    public class User implements QLModel {
        @Nullable private String id;
        @Nullable private String name;
        @Nullable private @Alias(name = "essai") String email;

        public User() {
        }

        public User(@Nullable String id,@Nullable String name,@Nullable String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        @Nullable
        public String getId() {
            return id;
        }

        public void setId(@Nullable String id) {
            this.id = id;
        }

        @Nullable
        public String getName() {
            return name;
        }

        public void setName(@Nullable String name) {
            this.name = name;
        }

        @Nullable
        public String getEmail() {
            return email;
        }

        public void setEmail(@Nullable String email) {
            this.email = email;
        }
    }

    public class Post implements QLModel {
        @Nullable private String id;
        @Nullable private String title;
        @Nullable private User user;

        public Post() {
        }

        public Post(@Nullable String id,@Nullable String title, @Nullable User user) {
            this.id = id;
            this.title = title;
            this.user = user;
        }

        @Nullable
        public String getId() {
            return id;
        }

        public void setId(@Nullable String id) {
            this.id = id;
        }

        @Nullable
        public String getTitle() {
            return title;
        }

        public void setTitle(@Nullable String title) {
            this.title = title;
        }

        @Nullable
        public User getUser() {
            return user;
        }

        public void setUser(@Nullable User user) {
            this.user = user;
        }
    }
}
