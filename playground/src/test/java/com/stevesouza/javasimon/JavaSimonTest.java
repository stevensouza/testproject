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
        long value = stopwatch.getLast();
        assertThat(value).isGreaterThanOrEqualTo(100);
        assertThat(stopwatch.getMax()).isEqualTo(value);
        assertThat(stopwatch.getMean()).isEqualTo(value);
        assertThat(stopwatch.getMin()).isEqualTo(value);
        assertThat(stopwatch.getTotal()).isEqualTo(value);

        assertThat(stopwatch.getActive()).isEqualTo(0);
        assertThat(stopwatch.getMaxActive()).isEqualTo(1);

        assertThat(stopwatch.getCounter()).isEqualTo(1);

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


    @Test
    public void testDisable() throws Exception {
        SimonManager.disable();
        Counter counter = SimonManager.getCounter("com.stevesouza.mycounter");
        counter.increase();
        assertThat(counter.getCounter()).isEqualTo(0);
        System.out.println("counter=" + counter);
        System.out.println("counter.sample="+counter.sample());
        System.out.println(SimonUtils.simonTreeString(SimonManager.getRootSimon()));
        SimonManager.enable();
    }

}