package com.stevesouza.jamon;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorComposite;

import java.util.Iterator;
import java.util.Set;

/**
 * Class that allows for iterating a collection that contains multiple instances of MonitorComposites and iterates
 * through them as if they were one MonitorComposite.  A sort of composite/collection of MonitorComposites.
 * <p>
 * Created by stevesouza on 8/16/14.
 */
public class MonitorCompositeIterator implements Iterator<Monitor> {
    private Iterator<MonitorComposite> iter;

    private MonitorComposite currentMonitorComposite;
    private int index = -1;

    public MonitorCompositeIterator(Set<MonitorComposite> monitorComposites) {
        iter = monitorComposites.iterator();
        currentMonitorComposite = iter.next();
    }

    @Override
    public boolean hasNext() {
        boolean hasMore;
        if (index < currentMonitorComposite.getNumRows() - 1) {
            hasMore = true;
        } else {
            hasMore = setNextMonitorComposite();
        }

        index++;
        return hasMore;
    }

    private boolean setNextMonitorComposite() {
        index = -1;
        if (iter.hasNext()) {
            currentMonitorComposite = iter.next();
            return currentMonitorComposite.getNumRows() > 0;
        }

        return false;
    }

    @Override
    public Monitor next() {
        return currentMonitorComposite.getMonitors()[index];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove is not supported in this class");
    }

    public MonitorComposite getCurrentMonitorComposite() {
        return currentMonitorComposite;
    }
}
