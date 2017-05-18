package com.applidium.graphql.client.di.logging;

import com.applidium.graphql.client.app.common.BaseActivity;
import com.applidium.graphql.client.app.common.BaseDialog;
import com.applidium.graphql.client.app.common.BaseDialogFragment;
import com.applidium.graphql.client.app.common.BaseFragment;
import com.applidium.graphql.client.app.common.BaseIntentService;
import com.applidium.graphql.client.app.common.BaseService;
import com.applidium.graphql.client.utils.aspect.TracerAspect;

import dagger.Subcomponent;

@Subcomponent
public interface LoggingComponent {
    void inject(BaseActivity injected);
    void inject(BaseDialog injected);
    void inject(BaseDialogFragment injected);
    void inject(BaseFragment injected);
    void inject(BaseIntentService injected);
    void inject(BaseService injected);

    void inject(TracerAspect tracerAspect);

    @Subcomponent.Builder
    interface Builder {
        LoggingComponent build();
    }
}
