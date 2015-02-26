package com.stevesouza.metrics;


import com.codahale.metrics.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codahale.metrics.MetricRegistry.*;

public class MetricsTest {
    private static MetricRegistry metrics = new MetricRegistry();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testTimer() throws Exception {
        long AS_NANO=100*1000000;
        Timer timer = metrics.timer(name(getClass(), "pageHits"));
        Timer.Context context=timer.time();
        Thread.sleep(100);
        long value = context.stop();
        Snapshot snapshot = timer.getSnapshot();
        assertThat(value).isGreaterThanOrEqualTo(AS_NANO);
        assertThat(snapshot.getMin()).isEqualTo(value);
        assertThat(snapshot.getMean()).isEqualTo(value);
        assertThat(snapshot.getMax()).isEqualTo(value);
        assertThat(snapshot.getMedian()).isEqualTo(value);
        assertThat(snapshot.get99thPercentile()).isEqualTo(value);
        assertThat(snapshot.size()).isEqualTo(1);
    }

    @Test
    public void testCounter() throws Exception {
        Counter counter = metrics.counter(name(getClass(), "exceptions"));
        counter.inc();
        counter.inc();
        counter.inc();
        counter.dec();
        assertThat(counter.getCount()).isEqualTo(2);
// To display statistics in jmx
//        final JmxReporter reporter = JmxReporter.forRegistry(metrics).build();
//        reporter.start();
//        Thread.sleep(1000000);
//        reporter.stop();
    }

}