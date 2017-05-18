package com.applidium.graphql.client.utils.crashes;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import com.applidium.graphql.client.Settings;
import com.applidium.graphql.client.app.common.BaseActivity;
import com.applidium.graphql.client.utils.trace.Tracer;

import net.hockeyapp.android.CrashManagerListener;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.logging.HttpLoggingInterceptor;

@Singleton @SuppressWarnings("squid:LeftCurlyBraceEndLineCheck") // Not applicable
public class HockeyAppCrashManagerListener extends CrashManagerListener implements
    Application.ActivityLifecycleCallbacks,
    HttpLoggingInterceptor.Logger,
    Tracer,
    ComponentCallbacks2
{
    private static final int MAX_NETWORK_LOGS = Settings.crashes.max_network;
    private static final int MAX_TRACE_LOGS = Settings.crashes.max_trace;
    private static final int MAX_COMPONENT_LOGS = Settings.crashes.max_component;

    private static final boolean ENABLED = Settings.crashes.enabled;
    private static final boolean ADDITIONAL_DATA = Settings.crashes.additional_data;
    private static final int BUFFER_SIZE = 128;

    private final LinkedList<String> activityStack = new LinkedList<>();
    private WeakReference<Activity> mostRecentActivity;

    private final LinkedList<String> networkLogs = new LinkedList<>();

    private final LinkedList<String> traceLogs = new LinkedList<>();

    private final LinkedList<String> componentLogs = new LinkedList<>();

    @Inject
    HockeyAppCrashManagerListener() {}

    @Override
    public boolean shouldAutoUploadCrashes() {
        return ENABLED;
    }

    @Override
    public String getDescription() {
        if (!ADDITIONAL_DATA) {
            return null;
        }

        String stack = getStackDescription();
        String view = getViewDescription();
        String network = getNetworkDescription();
        String trace = getTraceDescription();
        String component = getComponentDescription();

        return stack + view + network + trace + component;
    }

    private String getComponentDescription() {
        return formatLogs(componentLogs, "COMPONENT LOGS");
    }

    @NonNull
    private String formatLogs(LinkedList<String> logs, String title) {
        StringBuilder sb = new StringBuilder();

        sb.append(title).append('\n');

        String underline = new String(new char[title.length()]).replace('\0', '=');
        sb.append(underline).append("\n\n");

        for (String s : logs) {
            sb.append(s).append('\n');
        }

        sb.append("\n\n");
        return sb.toString();
    }

    private String getTraceDescription() {
        return formatLogs(traceLogs, "TRACE LOGS");
    }

    private String getStackDescription() {
        return formatLogs(activityStack, "ACTIVITY STACK");
    }

    private String getViewDescription() {
        StringBuilder sb = new StringBuilder(BUFFER_SIZE);
        sb.append("VIEW DUMP\n=========\n\n");

        if (mostRecentActivity != null) {
            Activity activity = mostRecentActivity.get();
            if (activity == null) {
                sb.append("Unable to dump view hierarchy\n");
            } else {
                View content = activity.findViewById(android.R.id.content);
                appendViewHierarchy(sb, "", content);
            }
        }

        sb.append("\n\n");
        return sb.toString();
    }

    private static void appendViewHierarchy(StringBuilder sb, String prefix, View view) {
        String desc = getPrintableView(prefix, view);
        sb.append(desc).append('\n');
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            String deepPrefix = deepenPrefix(prefix);
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View v = viewGroup.getChildAt(i);
                appendViewHierarchy(sb, deepPrefix, v);
            }
        }
    }

    @NonNull
    private static String deepenPrefix(String prefix) {
        if (prefix.length() == 0) {
            return "| " + prefix;
        }
        return "  " + prefix;
    }

    @NonNull
    private static String getPrintableView(String prefix, View view) {
        return String.format(Locale.getDefault(),
            "%s%s (%.0f, %.0f, %d, %d) %s",
            prefix,
            view.getClass().getSimpleName(),
            view.getX(),
            view.getY(),
            view.getWidth(),
            view.getHeight(),
            getPrintableId(view));
    }

    private static String getPrintableId(View view) {
        if (view.getId() == -1) {
            return "";
        }
        return view.getResources().getResourceName(view.getId());
    }


    private String getNetworkDescription() {
        return formatLogs(networkLogs, "NETWORK LOGS");
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        String desc = getActivityDesc(activity);
        activityStack.add(desc);
    }

    private String getActivityDesc(Activity activity) {
        String desc = getObjectDescription(activity);

        if (activity instanceof BaseActivity) {
            FragmentManager fragmentManager = ((BaseActivity) activity).getSupportFragmentManager();
            int backstackCount = fragmentManager.getBackStackEntryCount();
            StringBuilder builder = new StringBuilder(desc);
            for (int i = 0; i < backstackCount; i++) {
                builder.append("\n ").append(fragmentManager.getBackStackEntryAt(i).getName());
            }
            desc = builder.toString();
        }

        return desc;
    }

    private static String getObjectDescription(Object object) {
        int hash = System.identityHashCode(object);
        return String.format(
            Locale.getDefault(),
            "%s@%X",
            object.getClass().getSimpleName(),
            hash
        );
    }

    @Override
    public void onActivityStarted(Activity activity) {
        // no-op
    }

    @Override
    public void onActivityResumed(Activity activity) {
        mostRecentActivity = new WeakReference<>(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        // no-op
    }

    @Override
    public void onActivityStopped(Activity activity) {
        // no-op
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        // no-op
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activityStack.pop();
    }

    @Override
    public void log(String message) {
        if (networkLogs.size() == MAX_NETWORK_LOGS) {
            networkLogs.removeFirst();
        }
        networkLogs.add(message);
    }

    @Override
    public void trace(Object target, String message, Object[] parameterValues) {
        if (traceLogs.size() == MAX_TRACE_LOGS) {
            traceLogs.removeFirst();
        }
        String trace = getTraceMessage(target, message, parameterValues);
        traceLogs.add(trace);
    }

    @NonNull
    private String getTraceMessage(Object target, String message, Object... parameterValues) {
        StringBuilder traceBuilder = new StringBuilder();
        if (target != null) {
            traceBuilder.append(getObjectDescription(target));
            traceBuilder.append(" | ");
        }
        if (parameterValues != null && parameterValues.length > 0) {
            traceBuilder.append(String.format(message, parameterValues));
        } else {
            traceBuilder.append(message);
        }
        return traceBuilder.toString();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        String message = "onConfigurationChanged " + newConfig;
        addComponentMessage(message);
    }

    @Override
    public void onLowMemory() {
        addComponentMessage("onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        addComponentMessage("onTrimMemory " + level);
    }

    private void addComponentMessage(String message) {
        if (componentLogs.size() == MAX_COMPONENT_LOGS) {
            componentLogs.removeFirst();
        }
        componentLogs.add(message);
    }
}
