/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.statsd;


import com.timgroup.statsd.StatsDClient;
import com.timgroup.statsd.NonBlockingStatsDClient;

/**
 * To install StatsD and graphite I followed this links directions.  You simply download a script and run it!
 * http://markswanderingthoughts.nl/post/116538045615/getting-statsd-up-and-running-on-ubuntu-1404-in
* @author stevesouza
 */
public class StatsDPlay {
  // note the program runs whether or not statsd is running.
  private static final StatsDClient statsdClient = new NonBlockingStatsDClient("stevesouza.playground", "localhost", 8125);

  public static final void main(String[] args) throws InterruptedException {
    // added for loop with sleep as it seemed without them sometimes the metrics weren't added.  Probably 
    // due to lost udp packets
    for (int i=0;i<10;i++) {
        statsdClient.incrementCounter("mycounter");
        statsdClient.incrementCounter("testperiod.mycounter");
        statsdClient.recordGaugeValue("myguage", 100);
        statsdClient.recordExecutionTime("mytimer", 25);
        
        // unfortunately stasd and graphite don't allow "() .".  Parens are simply removed, " " is relaced with "_". "."
        // does works like a directory (heirarchy indicator).  So unfortunately method names don't work well in statsd.
        statsdClient.recordExecutionTime("execution(void com.stevesouza.jdk.JdkHelloWorld.urlWithConnection(String))", 50);
        
        // measures unique values...
        statsdClient.recordSetEvent("myset", "value1");
        statsdClient.recordSetEvent("myset", "value1");
        statsdClient.recordSetEvent("myset", "value2");
        statsdClient.recordSetEvent("myset", "value3");
        System.out.println(i+" loop of StatsD complete");
        Thread.sleep(250);
    }
  }
}
