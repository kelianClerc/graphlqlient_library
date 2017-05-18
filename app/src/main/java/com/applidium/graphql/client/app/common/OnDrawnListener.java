package com.applidium.graphql.client.app.common;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

class OnDrawnListener implements ViewTreeObserver.OnGlobalLayoutListener {

    private View view;
    private OnViewDrawn listener;

    /**
     * Set up a drawn listener. Clean any previous listener. The listener will be call on drawn and
     * then cleaned. You need to re install this for each drawn.
     * @param view The view to listen draw.
     * @param listener The callback trigger when view is drawn.
     */
    public void install(View view, OnViewDrawn listener) {
        clean();
        this.view = view;
        this.listener = listener;
        view.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        // The global layout listener is removed before calling back the OnViewDrawn listener, since
        // in this listener, view change could be applied which re trigger this
        // OnGlobalLayoutListener.
        removeOnGlobalLayoutListener();
        listener.onViewDrawn();
    }

    private void clean() {
        removeOnGlobalLayoutListener();
        view = null;
        listener = null;
    }

    private void removeOnGlobalLayoutListener() {
        if (view == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    public interface OnViewDrawn {
        void onViewDrawn();
    }
}
