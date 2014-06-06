package com.stevesouza;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import org.apache.log4j.Logger;

/**
 * Created by stevesouza on 5/26/14.
 */
public class Utils {
    private static final Logger LOG = Logger.getLogger(Utils.class);

    public static void displayJamon() {
        for (Monitor mon : MonitorFactory.getRootMonitor().getMonitors()) {
            LOG.info(mon);
        }
    }
}
