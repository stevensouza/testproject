package com.stevesouza.javasimon;

import org.javasimon.Counter;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.Stopwatch;
import org.javasimon.utils.SimonUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaSimonTest {

    @Before
    public void setUp() throws Exception {
        SimonManager.clear();
    }

    @After
    public void tearDown() throws Exception {
        SimonManager.clear();
    }

    @Test
    public void testStopWatch() throws Exception {
        Stopwatch stopwatch = SimonManager.getStopwatch("com.stevesouza.mystopwatch");
        Split split = stopwatch.start();
        Thread.sleep(100);
        split.stop();
        assertThat(stopwatch.getLast()).isGreaterThanOrEqualTo(100);
        long value = stopwatch.getLast();
        assertThat(stopwatch.getActive()).isEqualTo(0);
        assertThat(stopwatch.getCounter()).isGreaterThanOrEqualTo(1);
        assertThat(stopwatch.getMax()).isGreaterThanOrEqualTo(value);
        assertThat(stopwatch.getMaxActive()).isGreaterThanOrEqualTo(1);
        assertThat(stopwatch.getMean()).isGreaterThanOrEqualTo(value);
        assertThat(stopwatch.getMin()).isGreaterThanOrEqualTo(value);
        assertThat(stopwatch.getTotal()).isGreaterThanOrEqualTo(value);
        System.out.println("split="+split);
        System.out.println("stopWatch.sample="+stopwatch.sample());
        System.out.println(SimonUtils.simonTreeString(SimonManager.getRootSimon()));
    }

    @Test
    public void testCounter() throws Exception {
        Counter counter = SimonManager.getCounter("com.stevesouza.mycounter");
        counter.increase();
        counter.increase();
        counter.increase();
        counter.decrease();
        assertThat(counter.getCounter()).isEqualTo(2);
        assertThat(counter.getIncrementSum()).isEqualTo(3);
        assertThat(counter.getDecrementSum()).isEqualTo(1);
        System.out.println("counter=" + counter);
        System.out.println("counter.sample="+counter.sample());
        System.out.println(SimonUtils.simonTreeString(SimonManager.getRootSimon()));
    }
}