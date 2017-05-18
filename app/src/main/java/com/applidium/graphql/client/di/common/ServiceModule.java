package com.applidium.graphql.client.di.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.applidium.graphql.client.BuildConfig;
import com.applidium.graphql.client.Settings;
import com.applidium.graphql.client.data.net.retrofit.GraphQL_AndroidService;
import com.applidium.graphql.client.data.net.retrofit.model.RestError;
import com.applidium.graphql.client.utils.crashes.HockeyAppCrashManagerListener;
import com.applidium.graphql.client.utils.logging.InterceptorLogger;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.norberg.automatter.gson.AutoMatterTypeAdapterFactory;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ServiceModule {

    private static final String BASE_URL = Settings.network.base_url;
    private static final boolean LOG_NETWORK = Settings.network.logging.enabled;
    private static final int TIMEOUT = Settings.network.timeout_seconds;
    private static final String LOG_NETWORK_LEVEL = Settings.network.logging.level;
    private static final long CACHE_SIZE =
        Settings.network.cache.size_mb * 1024L * 1024L; // Mb in bytes
    private static final boolean ENABLE_CACHE = Settings.network.cache.enabled;

    private final File cacheDirectory;

    public ServiceModule(File cacheDirectory) {
        this.cacheDirectory = cacheDirectory;
    }

    @Provides @Singleton
    GraphQL_AndroidService provideService(Retrofit retrofit) {
        return retrofit.create(GraphQL_AndroidService.class);
    }

    @Provides @Singleton
    Converter<ResponseBody, RestError> provideConverter(Retrofit retrofit) {
        return retrofit.responseBodyConverter(RestError.class, new Annotation[0]);
    }

    @Provides @Singleton
    Retrofit provideRetrofit(OkHttpClient client, Converter.Factory converterFactory) {
        String baseUrl = sanitizeUrl(BASE_URL);
        return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(converterFactory)
            .build();
    }

    private String sanitizeUrl(String baseUrl) {
        if (baseUrl.endsWith("/")) {
            return baseUrl;
        }
        return baseUrl + "/";
    }

    @Provides @Singleton @Named("logging")
    HttpLoggingInterceptor provideLoggingInterceptor(HttpLoggingInterceptor.Logger logger) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(logger);
        if (BuildConfig.DEBUG && LOG_NETWORK) {
            interceptor.setLevel(getNetworkLogLevel());
        }
        return interceptor;
    }

    private HttpLoggingInterceptor.Level getNetworkLogLevel() {
        return HttpLoggingInterceptor.Level.valueOf(LOG_NETWORK_LEVEL);
    }

    @Provides @Singleton @Named("crashLogging")
    HttpLoggingInterceptor provideCrashLoggingInterceptor(HockeyAppCrashManagerListener listener) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(listener);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides @Singleton
    OkHttpClient provideClient(
        @Named("logging") HttpLoggingInterceptor loggingInterceptor,
        @Named("crashLogging") HttpLoggingInterceptor crashLogInterceptor,
        @Nullable Cache cache
    ) {
        return new OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(crashLogInterceptor)
            .addInterceptor(loggingInterceptor)
            .build();
    }

    @Provides @Singleton
    HttpLoggingInterceptor.Logger provideLogger(InterceptorLogger logger) {
        return logger;
    }

    @Provides @Singleton
    Converter.Factory provideConverterFactory() {
        return GsonConverterFactory.create(getGson());
    }

    @NonNull
    private Gson getGson() {
        AutoMatterTypeAdapterFactory autoMatterFactory = new AutoMatterTypeAdapterFactory();
        return new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapterFactory(autoMatterFactory)
            .create();
    }

    @Provides @Nullable @Singleton
    Cache provideCache(@Named("cacheDir") File cacheDirectory) {
        if (BuildConfig.DEBUG && !ENABLE_CACHE) {
            return null;
        }
        return new Cache(cacheDirectory, CACHE_SIZE);
    }

    @Provides @Named("cacheDir") @Singleton
    File provideCacheDirectory() {
        return cacheDirectory;
    }
}
