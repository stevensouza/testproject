package com.stevesouza.openmon;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import org.javasimon.Counter;
import org.javasimon.SimonManager;
import org.javasimon.Split;

/**
 * Created by stevesouza on 2/26/15.
 */
public class SimonImp implements OpenMon<Split> {
    @Override
    public Split start(String label) {
        return SimonManager.getStopwatch(label).start();
    }

    @Override
    public void stop(Split split) {
        split.stop();
        System.out.println("Simon split: "+split);
        System.out.println("Simon snapshot: "+split.getStopwatch().sample());

    }

    @Override
    public void exception(String label) {
        Counter mon = SimonManager.getCounter(label);
        mon.increase();
        System.out.println("Simon counter: "+mon);
    }
}
