package com.stevesouza.spring.contrib;

import com.jamonapi.MonKeyImp;
import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import com.jamonapi.utils.Misc;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//package org.springframework.aop.interceptor;

/**
 * Performance monitor interceptor that uses <b>JAMon</b> library
 * to perform the performance measurement on the intercepted method
 * and output the stats. In addition it can track/count exceptions
 * thrown by the intercepted method the stacktraces can be viewed in
 * the JAMon web application.
 *
 * <p>This code is inspired by Thierry Templier's blog.
 *
 * @author Dmitriy Kopylenko
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @since 1.1.3
 * @see com.jamonapi.MonitorFactory
 * @see org.springframework.aop.interceptor.PerformanceMonitorInterceptor
 */
@SuppressWarnings("serial")
public class JamonPerformanceMonitorInterceptor extends AbstractMonitoringInterceptor {
    private static final String EXCEPTION = "Exception";
    private boolean trackAllInvocations = false;
    private boolean trackExceptions = false;

    /**
     * Create a new JamonPerformanceMonitorInterceptor with a static logger.
     */
    public JamonPerformanceMonitorInterceptor() {
    }

    /**
     * Create a new JamonPerformanceMonitorInterceptor with a dynamic or static logger,
     * according to the given flag.
     * @param useDynamicLogger whether to use a dynamic logger or a static logger
     * @see #setUseDynamicLogger(boolean)
     */
    public JamonPerformanceMonitorInterceptor(boolean useDynamicLogger) {
        setUseDynamicLogger(useDynamicLogger);
    }

    /**
     * Create a new JamonPerformanceMonitorInterceptor with a dynamic or static logger,
     * according to the given flag.
     * @param useDynamicLogger whether to use a dynamic logger or a static logger
     * @param trackAllInvocations whether to track all invocations that go through
     * this interceptor, or just invocations with trace logging enabled
     * @see #setUseDynamicLogger(boolean)
     * @see #setTrackAllInvocations(boolean)
     */
    public JamonPerformanceMonitorInterceptor(boolean useDynamicLogger, boolean trackAllInvocations) {
        this(useDynamicLogger, trackAllInvocations, false);
    }

    /**
     * Create a new JamonPerformanceMonitorInterceptor with a dynamic or static logger,
     * according to the given flags.
     * @param useDynamicLogger whether to use a dynamic logger or a static logger
     * @param trackAllInvocations whether to track all invocations that go through
     * this interceptor, or just invocations with trace logging enabled
     * @param trackExceptions whether to track/count exceptions that are thrown
     * @see #setUseDynamicLogger(boolean)
     * @see #setTrackAllInvocations(boolean)
     * @see #setTrackExceptions(boolean)
     * @since 4.1
     */
    public JamonPerformanceMonitorInterceptor(boolean useDynamicLogger, boolean trackAllInvocations, boolean trackExceptions) {
        setUseDynamicLogger(useDynamicLogger);
        setTrackAllInvocations(trackAllInvocations);
        setTrackExceptions(trackExceptions);
    }

    /**
     * Set whether to track all invocations that go through this interceptor,
     * or just invocations with trace logging enabled.
     * <p>Default is "false": Only invocations with trace logging enabled will
     * be monitored. Specify "true" to let JAMon track all invocations,
     * gathering statistics even when trace logging is disabled.
     */
    public void setTrackAllInvocations(boolean trackAllInvocations) {
        this.trackAllInvocations = trackAllInvocations;
    }

    /**
     * Set whether to track/count exceptions thrown by invocations that go through this interceptor.
     * In addition stack traces will be able available to be viewed with the JAMon web application.
     * <p>For backwards compatibility the default is "false", however it is recommended that
     * it is enabled.</p>
     * @since  4.1
     */
    public void setTrackExceptions(boolean trackExceptions) {
        this.trackExceptions = trackExceptions;
    }


    /**
     * Always applies the interceptor if the "trackAllInvocations" flag has been set;
     * else just kicks in if the log is enabled.
     * @see #setTrackAllInvocations
     * @see #isLogEnabled
     */
    @Override
    protected boolean isInterceptorEnabled(MethodInvocation invocation, Log logger) {
        return (this.trackAllInvocations || isLogEnabled(logger));
    }

    /**
     * Wraps the invocation with a JAMon Monitor and writes the current
     * performance statistics to the log (if enabled).
     * @see com.jamonapi.MonitorFactory#start
     * @see com.jamonapi.Monitor#stop
     */
    @Override
    protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
        String name = createInvocationTraceName(invocation);
        MonKeyImp key = new MonKeyImp(name, name, "ms.");
        Monitor monitor = MonitorFactory.start(key);
        try {
            return invocation.proceed();
        } catch (Throwable t) {
            if (this.trackExceptions) {
                trackException(key, t);
            }
            throw t;
        } finally {
            monitor.stop();
            if (!this.trackAllInvocations || isLogEnabled(logger)) {
                logger.trace("JAMon performance statistics for method [" + name + "]:\n" + monitor);
            }
        }
    }


    /**
     * Count the thrown exception and also put the stack trace in the details portion of the key.
     * This will allow the stack trace to be viewed in the JAMon web application.
     * @since 4.1
     */
    private void trackException(MonKeyImp key, Throwable exception) {
        String stackTrace = new StringBuilder()
                .append("stackTrace=")
                .append(Misc.getExceptionTrace(exception))
                .toString();

        key.setDetails(stackTrace);
        // Specific exception counter. Example: java.lang.RuntimeException
        MonitorFactory.add(new MonKeyImp(exception.getClass().getName(), stackTrace, EXCEPTION), 1);
        // General exception counter which is a total for all exceptions thrown
        MonitorFactory.add(new MonKeyImp(MonitorFactory.EXCEPTIONS_LABEL, stackTrace, EXCEPTION), 1);
    }

}

