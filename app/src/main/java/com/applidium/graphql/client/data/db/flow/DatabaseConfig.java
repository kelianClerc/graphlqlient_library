package com.applidium.graphql.client.data.db.flow;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = DatabaseConfig.NAME, version = DatabaseConfig.VERSION)
public class DatabaseConfig {
    public static final String NAME = "GraphQL_Android";
    public static final int VERSION = 1;
}
