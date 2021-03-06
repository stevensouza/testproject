package com.stevesouza.automon.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TimeExpirableTest {

    private static long MIN_IN_MS = 60000;

    @Test
    public void testHasExpired() throws Exception {
        TimeExpirable expirable = new TimeExpirable(0);
        assertThat(expirable.isExpired()).isTrue();

        expirable = new TimeExpirable(1);
        assertThat(expirable.isExpired()).isFalse();
    }

    @Test
    public void testConvertToMs() throws Exception {
        assertThat(TimeExpirable.convertToMs(0)).describedAs("Should convert from minutes to ms.").isEqualTo(0);
        assertThat(TimeExpirable.convertToMs(1)).describedAs("Should convert from minutes to ms.").isEqualTo(MIN_IN_MS);
    }

    @Test
    public void testHasExpired_SimulatingExpiration() throws Exception {
        TimeExpirable.Now now = mock(TimeExpirable.Now.class);
        long time = System.currentTimeMillis();
        when(now.now()).thenReturn(time);
        TimeExpirable expirable = new TimeExpirable(1, now);
        when(now.now()).thenReturn(time+MIN_IN_MS);
        assertThat(expirable.isExpired()).describedAs("Exactly at expiration interval").isTrue();
    }


    @Test
    public void testHasExpired_SimulatingNonExpiration() throws Exception {
        TimeExpirable.Now now = mock(TimeExpirable.Now.class);
        long time = System.currentTimeMillis();
        when(now.now()).thenReturn(time);
        TimeExpirable expirable = new TimeExpirable(1, now);
        when(now.now()).thenReturn(time+100);
        assertThat(expirable.isExpired()).isFalse();
    }


}