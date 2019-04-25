package com.stevesouza.micrometer;

import com.jamonapi.BasicTimingMonitor;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.concurrent.TimeUnit;

public class MicrometerPlay {

    public static void main(String[] args) {
        System.out.println("playing with micrometer");
        MeterRegistry registry = new SimpleMeterRegistry();
        Timer timer = Timer.builder("app.timer")
                .description("my description")
                .tag("type", "method")
                .register(registry);

        BasicTimingMonitor m = new BasicTimingMonitor();
        m.start();
        sleep(300);
        timer.record(m.stop(), TimeUnit.MILLISECONDS);

        System.out.println("timer count=" + timer.count());
        System.out.println("timer.totaltime=" + timer.totalTime(TimeUnit.MILLISECONDS));


        Counter counter = Counter.builder("exceptions")
                .tag("error", "exception")
                .description("my other description")
                .register(registry);
        counter.increment();
        counter.increment();

        System.out.println("count=" + counter.count());


    }


    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
