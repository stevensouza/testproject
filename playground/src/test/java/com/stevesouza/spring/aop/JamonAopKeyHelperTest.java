package com.stevesouza.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.elasticsearch.common.mvel2.ast.Sign;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JamonAopKeyHelperTest {

    private static final String SIGNATURE = "void com.stevesouza.spring.MonitorMeClass.anotherMethod(String)";
    private static final String EXCEPTION_LABEL = FileNotFoundException.class.getName();

    private JamonAopKeyHelper helper = new JamonAopKeyHelper();

    private ProceedingJoinPoint pjp;
    private Signature signature;

    @Before
    public void setUp() {
        pjp = mock(ProceedingJoinPoint.class);
        signature  = mock(Signature.class);
        when(pjp.getSignature()).thenReturn(signature);
        when(signature.toString()).thenReturn(SIGNATURE);
    }

    @Test
    public void testGetLabel() throws Exception {
       assertThat(helper.getLabel(pjp)).isEqualTo(SIGNATURE);
    }

    @Test
    public void testGetExceptionLabel() throws Exception {
        assertThat(helper.getExceptionLabel(new FileNotFoundException())).isEqualTo(EXCEPTION_LABEL);
    }

    @Test
    public void testGetDetails() throws Exception {
        assertThat(helper.getDetails(pjp)).isEqualTo(SIGNATURE);
    }

    @Test
    public void testGetDetails_WithException() throws Exception {
        assertThat(helper.getDetails(pjp, new FileNotFoundException())).startsWith("stackTrace=java.io.FileNotFoundException");
    }
}