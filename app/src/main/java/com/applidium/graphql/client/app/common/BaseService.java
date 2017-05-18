package com.applidium.graphql.client.app.common;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.applidium.graphql.client.utils.logging.Logger;

import javax.inject.Inject;

public abstract class BaseService extends Service {

    @Inject protected Logger logger;

    @Override
    public void onCreate() {
        super.onCreate();
        injectDependencies();
        logger.v(this, "onCreate()");
    }

    protected abstract void injectDependencies();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String s = "onStartCommand(intent: %s, flags: %s , startId: %s";
        logger.v(this, s, intent, flags, startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        logger.v(this, "onBind(intent: %s)", intent);
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        logger.v(this, "onUnbind(intent: %s)", intent);
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        logger.v(this, "onRebind(intent: %s)", intent);
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        logger.v(this, "onDestroy()");
        super.onDestroy();
    }
}
