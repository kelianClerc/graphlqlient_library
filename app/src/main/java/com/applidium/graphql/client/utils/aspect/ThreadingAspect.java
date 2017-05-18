package com.applidium.graphql.client.utils.aspect;

import android.support.annotation.NonNull;

import com.applidium.graphql.client.di.ComponentManager;
import com.applidium.graphql.client.utils.logging.Logger;
import com.applidium.graphql.client.utils.threading.PostExecutionThread;
import com.applidium.graphql.client.utils.threading.ThreadExecutor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import javax.inject.Inject;

@Aspect
public class ThreadingAspect {

    private static final String ANNOTATION_PACKAGE =
        "com.applidium.graphql.client.utils.threading.";

    public static final String EXECUTION_PREFIX = "execution(@" + ANNOTATION_PACKAGE;
    public static final String EXECUTION_SUFFIX = " * *(..))";

    private static final String EXECUTION_THREAD_POINTCUT =
        EXECUTION_PREFIX + "RunOnExecutionThread" + EXECUTION_SUFFIX;

    private static final String POST_EXECUTION_THREAD_POINTCUT =
        EXECUTION_PREFIX + "RunOnPostExecutionThread" + EXECUTION_SUFFIX;

    @Inject
    ThreadExecutor threadExecutor;
    @Inject
    PostExecutionThread postExecutionThread;
    @Inject
    Logger logger;

    public void init() {
        ComponentManager.getThreadingComponent().inject(this);
    }

    @Pointcut(EXECUTION_THREAD_POINTCUT)
    public void executionThreadAnnotated() { /* no-op */ }

    @Pointcut(POST_EXECUTION_THREAD_POINTCUT)
    public void postExecutionThreadAnnotated() { /* no-op */ }

    @Around("executionThreadAnnotated()")
    public void runOnExecutionThread(ProceedingJoinPoint joinPoint) {
        checkInit();
        logger.v(this, "Running execution joint point " + joinPoint);
        threadExecutor.execute(makeExecutorRunnable(joinPoint));
    }

    private Runnable makeExecutorRunnable(final ProceedingJoinPoint joinPoint) {
        return new Runnable() {
            @Override
            public void run() {
                proceed(joinPoint);
            }
        };
    }

    @Around("postExecutionThreadAnnotated()")
    public void runOnPostExecutionThread(ProceedingJoinPoint joinPoint) {
        checkInit();
        logger.v(this, "Running post execution joint point " + joinPoint);
        postExecutionThread.post(makePostRunnable(joinPoint));
    }

    private Runnable makePostRunnable(final ProceedingJoinPoint joinPoint) {
        return new Runnable() {
            @Override
            public void run() {
                proceed(joinPoint);
            }
        };
    }

    @SuppressWarnings(
        "checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck"
    ) // Not applicable in this aspect
    private void proceed(ProceedingJoinPoint joinPoint) {
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            String message = makeThrowableMessage(joinPoint, throwable);
            logger.e(this, throwable, message);
            throw new IllegalStateException("Halt and catch fire");
        }
    }

    @NonNull
    private String makeThrowableMessage(ProceedingJoinPoint joinPoint, Throwable throwable) {
        String message;
        if (throwable instanceof RuntimeException) {
            message = "Illegal RuntimeException " +
                throwable.getClass().getSimpleName() +
                ", you must catch it within " +
                joinPoint.getSignature().toShortString();
        } else if (throwable instanceof Exception) {
            message = "Illegal Exception " +
                throwable.getClass().getSimpleName() +
                ", your method " +
                joinPoint.getSignature().toShortString() +
                " can not throw exception during threading";
        } else {
            message = "Error during threading";
        }
        return message;
    }

    private void checkInit() {
        if (logger == null || threadExecutor == null || postExecutionThread == null) {
            fail();
        }
    }

    @SuppressWarnings("squid:S00112") // Not applicable here
    private static void fail() {
        String message = "ThreadingAspect#init() was not called on Application#onCreate()";
        throw new RuntimeException(message);
    }
}
