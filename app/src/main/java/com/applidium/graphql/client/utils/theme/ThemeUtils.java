package com.applidium.graphql.client.utils.theme;

import android.content.Context;
import android.support.annotation.UiThread;
import android.util.TypedValue;

import com.applidium.graphql.client.R;

public final class ThemeUtils {

    private static final TypedValue TYPED_VALUE = new TypedValue();

    private ThemeUtils() {}

    @UiThread
    public static void ensureRuntimeTheme(Context context) {
        context.getTheme().resolveAttribute(R.attr.runtimeTheme, TYPED_VALUE, true);
        if (TYPED_VALUE.resourceId != 0) {
            context.setTheme(TYPED_VALUE.resourceId);
        }
    }
}
