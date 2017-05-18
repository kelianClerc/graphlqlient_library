package com.applidium.graphql.client.utils.aspect;

import android.support.annotation.NonNull;

import com.applidium.graphql.client.di.ComponentManager;
import com.applidium.graphql.client.utils.trace.Tracer;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;

import javax.inject.Inject;

@Aspect
public class TracerAspect {

    private static final String ANNOTATION_PACKAGE =
        "com.applidium.graphql.client.utils.trace.";
    private static final String TRACE_POINTCUT =
        "execution(@" + ANNOTATION_PACKAGE + "Trace * *(..))";

    @Inject Tracer tracer;

    public void init() {
        ComponentManager.getLoggingComponent().inject(this);
    }

    @Pointcut(TRACE_POINTCUT)
    public void traceAnnontated() { /* no-op */ }

    @Before("traceAnnontated()")
    public void trace(JoinPoint joinPoint) {
        checkInit();

        Object target = joinPoint.getTarget();
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        Object[] parameterValues = joinPoint.getArgs();

        String message = makeMessage(codeSignature);

        tracer.trace(target, message, parameterValues);
    }

    @NonNull
    private String makeMessage(CodeSignature codeSignature) {
        String methodName = codeSignature.getName();
        String[] parameterNames = codeSignature.getParameterNames();

        String params = makeParamsFormat(parameterNames);
        return methodName + "(" + params + ")";
    }

    @NonNull
    private String makeParamsFormat(String... parameterNames) {
        StringBuilder builder = new StringBuilder();
        for (String parameterName : parameterNames) {
            builder.append(parameterName);
            builder.append(": %s,");
        }
        int index = builder.lastIndexOf(",");
        if (index >= 0) {
            builder.deleteCharAt(index);
        }
        return builder.toString();
    }

    private void checkInit() {
        if (tracer == null) {
            fail();
        }
    }

    @SuppressWarnings("squid:S00112") // Not applicable here
    private static void fail() {
        String message = "TracerAspect#init() was not called on Application#onCreate()";
        throw new RuntimeException(message);
    }
}
