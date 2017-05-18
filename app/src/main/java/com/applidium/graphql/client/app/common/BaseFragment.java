package com.applidium.graphql.client.app.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.applidium.graphql.client.utils.logging.Logger;

import javax.inject.Inject;

public abstract class BaseFragment extends Fragment {

    @Inject protected Logger logger;
    private OnDrawnListener onDrawnListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
        logger.v(this, "onCreate(savedInstanceState: %s)", savedInstanceState);
    }

    protected abstract void injectDependencies();

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addTreeObserver(view);
    }

    private void addTreeObserver(View view) {
        if (onDrawnListener == null) {
            onDrawnListener = new OnDrawnListener();
        }
        onDrawnListener.install(view, getOnViewDrawnListener());
    }

    @NonNull
    private OnDrawnListener.OnViewDrawn getOnViewDrawnListener() {
        return new OnDrawnListener.OnViewDrawn() {
            @Override
            public void onViewDrawn() {
                BaseFragment.this.onViewDrawn();
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
