package com.stevesouza.automon;

import org.aspectj.lang.JoinPoint;
import org.automon.implementations.NullImp;
import org.automon.implementations.SysOut;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import org.automon.implementations.TimerContext;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AutomonPlayTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSysOut() {
        JoinPoint jp = mock(JoinPoint.class);
        JoinPoint.StaticPart sp = mock(JoinPoint.StaticPart.class);
        SysOut so = new SysOut();
        TimerContext context = new TimerContext(sp);
        so.exception(jp, new RuntimeException("my exception"));
        so.start(sp);
        so.stop(context);
        so.stop(context, new RuntimeException("my exception"));
    }
}