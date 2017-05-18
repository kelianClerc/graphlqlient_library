package com.applidium.graphql.client.di.logging;

import com.applidium.graphql.client.BuildConfig;
import com.applidium.graphql.client.Settings;
import com.applidium.graphql.client.utils.logging.Logger;
import com.applidium.graphql.client.utils.logging.NoOpLogger;
import com.applidium.graphql.client.utils.logging.TimberLogger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LoggingModule {

    protected static final boolean SHOW_HASH = Settings.logging.show_hashs;
    protected static final boolean ENABLED = Settings.logging.enabled;

    @Provides @Singleton
    Logger provideLogger() {
        return getLogger();
    }

    protected Logger getLogger() {
        if (BuildConfig.DEBUG && ENABLED) {
            return new TimberLogger(SHOW_HASH);
        }
        return new NoOpLogger();
    }
}
