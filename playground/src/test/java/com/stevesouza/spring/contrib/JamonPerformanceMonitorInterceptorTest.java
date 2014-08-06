
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


package com.stevesouza.spring.contrib;

import com.jamonapi.MonitorFactory;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.when;


public class JamonPerformanceMonitorInterceptorTest {
    private JamonPerformanceMonitorInterceptor interceptor = new JamonPerformanceMonitorInterceptor();
    private MethodInvocation invocation = mock(MethodInvocation.class);
    private Log log = mock(Log.class);

    @Before
    public void setUp() {
       MonitorFactory.reset();
    }

    @After
    public void tearDown() {
        MonitorFactory.reset();
    }

    @Test
    public void testInvokeUnderTraceWithNormalProcessing() throws Throwable {
        String string = "helloworld";
        Method anyMethod = string.getClass().getMethod("toString");
        when(invocation.getMethod()).thenReturn(anyMethod);

        interceptor.invokeUnderTrace(invocation, log);

        assertEquals("A monitor should exist for the method being invoked", 1, MonitorFactory.getNumRows());
        assertTrue("The jamon report should contain the toString method invocation", MonitorFactory.getReport().contains("toString"));
    }


    @Test
    public void testInvokeUnderTraceWithException() throws Throwable {
        Object anyObject = mock(Object.class);
        Method anyMethod=anyObject.getClass().getMethod("toString");
        when(invocation.getMethod()).thenReturn(anyMethod);
        Throwable expectedException = new RuntimeException("Simulating thrown exception");
        when(invocation.proceed()).thenThrow(expectedException);
        Throwable actualException = null;

        try {
            interceptor.invokeUnderTrace(invocation, log);
        } catch (Throwable t) {
           actualException = t;
        }

        assertEquals("The method being monitored should throw an exception", expectedException, actualException);
        assertEquals("A monitor should exist for the method being invoked", 1, MonitorFactory.getNumRows());
        assertTrue("The jamon report should contain the toString method invocation",  MonitorFactory.getReport().contains("toString"));
    }

    @Test
    public void testInvokeUnderTraceWithExceptionTracking() throws Throwable {
        interceptor.setTrackExceptions(true);
        Object anyObject = mock(Object.class);
        Method anyMethod = anyObject.getClass().getMethod("toString");
        when(invocation.getMethod()).thenReturn(anyMethod);
        Throwable expectedException = new RuntimeException("Simulating thrown exception");
        when(invocation.proceed()).thenThrow(expectedException);
        Throwable actualException = null;

        try {
            interceptor.invokeUnderTrace(invocation, log);
        } catch (Throwable t) {
            actualException = t;
        }

        assertEquals("The method being monitored should throw an exception", expectedException, actualException);
        assertEquals("Monitors should exist for the method invocation and 2 exceptions", 3, MonitorFactory.getNumRows());
        assertTrue("The jamon report should contain the toString method invocation", MonitorFactory.getReport().contains("toString"));
        assertTrue("The jamon report should contain the generic exception: "+MonitorFactory.EXCEPTIONS_LABEL,  MonitorFactory.getReport().contains(MonitorFactory.EXCEPTIONS_LABEL));
        assertTrue("The jamon report should contain the specific exception: RuntimeException'",  MonitorFactory.getReport().contains("RuntimeException"));
    }
}