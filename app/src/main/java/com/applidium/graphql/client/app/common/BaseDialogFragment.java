package com.applidium.graphql.client.app.common;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.view.ViewTreeObserver;

import com.applidium.graphql.client.utils.logging.Logger;

import javax.inject.Inject;

public abstract class BaseDialogFragment extends AppCompatDialogFragment {

    @Inject protected Logger logger;

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
        ViewTreeObserver.OnGlobalLayoutListener listener = getLayoutListener(view);
        view.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    @NonNull
    private ViewTreeObserver.OnGlobalLayoutListener getLayoutListener(final View view) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        logger.v(this, "onCreateDialog(savedInstanceState: %s)", savedInstanceState);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        logger.v(this, "onDismiss(dialog: %s)", dialog);
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
