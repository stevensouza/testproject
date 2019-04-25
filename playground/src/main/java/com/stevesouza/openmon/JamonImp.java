package com.stevesouza.openmon;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

/**
 * Created by stevesouza on 2/26/15.
 */
public class JamonImp implements OpenMon<Monitor> {
    @Override
    public Monitor start(String label) {
        return MonitorFactory.start(label);
    }

    @Override
    public void stop(Monitor mon) {
        mon.stop();
        System.out.println(mon);
    }

    @Override
    public void exception(String label) {
        Monitor mon = MonitorFactory.add(label, "Exception", 1);
        System.out.println(mon);
    }

    @Override
    public void enable(boolean enable) {
        if (enable) {
            MonitorFactory.enable();
        } else {
            MonitorFactory.disable();
        }
    }

    @Override
    public boolean isEnabled() {
        return MonitorFactory.isEnabled();
    }
}
