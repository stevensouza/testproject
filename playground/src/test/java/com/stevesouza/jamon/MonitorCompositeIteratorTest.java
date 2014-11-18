package com.stevesouza.jamon;

import com.jamonapi.MonitorComposite;
import com.jamonapi.MonitorFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


public class MonitorCompositeIteratorTest {

    @Before
    public void setUp() throws Exception {
        MonitorFactory.reset();
    }

    @After
    public void tearDown() throws Exception {
        MonitorFactory.reset();
    }

    @Test
    public void testWorksWithOneComposite() throws Exception {
        MonitorFactory.start("hello").stop();
        MonitorFactory.start("world").stop();
        MonitorComposite monitorComposite = MonitorFactory.getRootMonitor();
        Set<MonitorComposite> set = new HashSet<MonitorComposite>();
        set.add(monitorComposite);
        MonitorCompositeIterator iter = new MonitorCompositeIterator(set);
        int size = size(iter);

        assertThat(size).isEqualTo(monitorComposite.getNumRows());
    }

    private int size(MonitorCompositeIterator iter) {
        int size = 0;
        while (iter.hasNext()) {
            size++;
        }
        return size;
    }

    @Test
    public void testWorksWithMultipleComposites() throws Exception {
        MonitorFactory.start("hello").stop();
        MonitorFactory.start("world").stop();
        MonitorComposite monitorComposite1 = MonitorFactory.getRootMonitor();
        MonitorFactory.reset();

        MonitorFactory.start("hello").stop();
        MonitorFactory.start("world").stop();
        MonitorFactory.add("page", "counter", 1);
        MonitorComposite monitorComposite2 = MonitorFactory.getRootMonitor();

        Set<MonitorComposite> set = new HashSet<MonitorComposite>();
        set.add(monitorComposite1);
        set.add(monitorComposite2);

        MonitorCompositeIterator iter = new MonitorCompositeIterator(set);
        int size = size(iter);

        assertThat(size).isEqualTo(monitorComposite1.getNumRows() + monitorComposite2.getNumRows());

    }

}

