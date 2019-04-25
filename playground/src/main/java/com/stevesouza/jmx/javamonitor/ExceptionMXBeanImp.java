package com.stevesouza.jmx.javamonitor;

import com.jamonapi.JAMonListener;
import com.jamonapi.MonitorFactory;

/**
 * Created by stevesouza on 11/19/14.
 */
public class ExceptionMXBeanImp implements ExceptionMXBean {
    private static final String LABEL = "com.jamonapi.Exceptions";
    private static final String UNITS = "Exception";

    // in jamonservletcontextlistener
    //         MonitorFactory.addListeners(loader.getListeners());
    //         Monitor mon =  MonitorFactory.getMonitor(listenerInfo.getLabel(), listenerInfo.getUnits());
    //         mon.addListener(listenerInfo.getListenerType(), JAMonListenerFactory.get(listenerInfo.getListenerName()));


    @Override
    public String getMostRecentStackTrace() {
        if (MonitorFactory.exists(LABEL, UNITS) && MonitorFactory.getMonitor(LABEL, UNITS).hasListener("value", "FIFOBuffer")) {
            JAMonListener listener = MonitorFactory.getMonitor(LABEL, UNITS).getListenerType("value").getListener("FIFOBuffer");
            return "my stack trace (has listener)!!!!";
        }
        return "You must enable a FIFOBuffer to see the most recent stack trace";
    }

    @Override
    public long getExceptions() {
        return Utils.getCount(LABEL, UNITS);
    }
}
