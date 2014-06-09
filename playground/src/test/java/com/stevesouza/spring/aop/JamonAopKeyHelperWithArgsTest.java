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

public class JamonAopKeyHelperWithArgsTest {
    private static final String SIGNATURE = "void com.stevesouza.spring.MonitorMeClass.anotherMethod(String)";
    private static final String EXCEPTION_LABEL = FileNotFoundException.class.getName();

    private JamonAopKeyHelperWithArgs helper = new JamonAopKeyHelperWithArgs();

    private ProceedingJoinPoint pjp;
    private Signature signature;

    @Test
    @Before
    public void setUp() {
        pjp = mock(ProceedingJoinPoint.class);
        signature  = mock(Signature.class);
        when(pjp.getSignature()).thenReturn(signature);
        when(signature.toString()).thenReturn(SIGNATURE);
    }

    @Test
    public void testGetDetails_NoArgs() throws Exception {
        assertThat(helper.getDetails(pjp)).isEqualTo(SIGNATURE);
    }

    @Test
    public void testGetDetails_OneArg() throws Exception {
        String answer = SIGNATURE+"\n"+
                        "arguments(1):\n"+
                        "firstArg";
        when(pjp.getArgs()).thenReturn(new Object[]{"firstArg"});
        assertThat(helper.getDetails(pjp)).isEqualTo(answer);
    }

    @Test
    public void testGetDetails_TwoArg() throws Exception {
        String answer = SIGNATURE+"\n"+
                "arguments(2):\n"+
                "firstArg,\n"+
                "secondArg";
        when(pjp.getArgs()).thenReturn(new Object[]{"firstArg", "secondArg"});
        assertThat(helper.getDetails(pjp)).isEqualTo(answer);
    }


    @Test
    public void testGetDetails_ThreeArg() throws Exception {
        String answer = SIGNATURE+"\n"+
                "arguments(3):\n"+
                "firstArg,\n"+
                "secondArg,\n"+
                "thirdArg";
        when(pjp.getArgs()).thenReturn(new Object[]{"firstArg", "secondArg", "thirdArg"});
        assertThat(helper.getDetails(pjp)).isEqualTo(answer);
    }

    @Test
        public void testGetDetails_WithException() throws Exception {
        assertThat(helper.getDetails(pjp, new FileNotFoundException())).startsWith("stackTrace=java.io.FileNotFoundException");
    }

    @Test
    public void testGetDetails_WithException_ThreeArg() throws Exception {
        String argsAnswer = "arguments(3):\n"+
        "firstArg,\n"+
        "secondArg,\n"+
        "thirdArg";
        when(pjp.getArgs()).thenReturn(new Object[]{"firstArg", "secondArg", "thirdArg"});
        assertThat(helper.getDetails(pjp, new FileNotFoundException())).startsWith("stackTrace=java.io.FileNotFoundException");
        assertThat(helper.getDetails(pjp, new FileNotFoundException())).contains(argsAnswer);

    }
}