# GraphQLient Library

GraphQLient Library is the core part of the GraphQLient bundle.
It is designed to ease communication between an Android application and a GraphQL server.

It goes along with gradle plugin named 
[GraphQLientPlugin which can be found here](https://github.com/kelianClerc/graphqlient_gradle_plugin)

`GraphQLient Plugin` computes `.graphql` files containing valid GraphQL queries to create an object 
implementing `QLRequest` and another implementing `QLResponseModel`.


Once it has a `QLRequest`, *GraphQLient Library* can communicate with the server. It uses `OkHttp` client to 
send requests. When it receives the response, it returns a well completed `QLResponseModel` associated
 with the QLRequest`.
 
Whichever JSON parser can be interfaced by implementing `Converter.
An example with [Gson](https://github.com/google/gson) is implemented in the folder [gson]()

This library could work without the plugin but you would have to do yourself the job done by 
`GraphQLientPlugin`. 
 
 ## How to use GraphQLient Library
 
 #### GraphQL instance
 Create a `GraphQL` object and provide endpoint address and the converter's factory you want to use.
 
 Example :
 ```java
 String url = "http://localhost:3000/graphql/test";
 Gson gson = new GsonBuilder().create();
 GsonConverterFactory converterFactory = GsonConverterFactory.create(gson);
 GraphQL graphQL = new GraphQL.Builder()
     .baseUrl(url)
     .converterFactory(converterFactory)
     .build();
 ```

#### Synchrone request
Send a request using send` method and passing a QLRequest object.

Example :
```java
UserListRequest request = new UserListRequest();
QLResponse<UserListResponse> response = graphql.send(request);
```

The response received by the client is mapped to `QLResponseModel` object 
corresponding to the `QLRequest`.\
`QLResponse` contains an instance of a completed `QLResponseModel` and the raw response from
`OkHttp`.

#### Asynchrone request
In asynchronous architecture, `GraphQL` object provides a `QLCall` that can be enqueued.

Example : 
```java
UserListRequest request = new UserListRequest();
QLCall<UserListResponse> call = graphql.call(request);
```

## Download

Add `jitpack` repository in project's `build.gradle`

```
buildscript {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        classpath 'com.github.kelianClerc:graphqlient_gradle_plugin:develop-SNAPSHOT'
    }
}

allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

```

Add to module's `build.gradle` :
```
apply plugin: 'com.applidium.qlrequest'

dependencies {
    compile 'com.github.kelianClerc.graphlqlient_library:graphqlient:498b237d09'
    compile 'com.github.kelianClerc.graphlqlient_library:gson:498b237d09'
}
```


