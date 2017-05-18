package com.applidium.graphql.client.app.common;

import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.applidium.graphql.client.utils.logging.Logger;

import javax.inject.Inject;

public abstract class BaseDialog extends DialogFragment {

    @Inject protected Logger logger;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        logger.v(this, "onCreate(savedInstanceState: %s)", savedInstanceState);
    }

    protected abstract void injectDependencies();

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addTreeObserver(view);
    }

    private void addTreeObserver(View view) {
        OnGlobalLayoutListener listener = getLayoutListener(view);
        view.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    @NonNull
    private OnGlobalLayoutListener getLayoutListener(final View view) {
        return new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                removeGlobalListener();
                onViewDrawn();
            }

            private void removeGlobalListener() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        logger.v(this, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        logger.v(this, "onResume()");
    }

    /**
     * Called when the view activity is actually drawn for the first time. Activity views sizes are
     * then correctly initialized.
     */
    protected void onViewDrawn() {
        logger.v(this, "onViewDrawn()");
    }

    @Override
    public void onPause() {
        super.onPause();
        logger.v(this, "onPause()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Does not log outState since child may update outState, resulting in false logging.
        logger.v(this, "onSaveInstanceState()");
    }

    @Override
    public void onStop() {
        super.onStop();
        logger.v(this, "onStop()");
    }
}
