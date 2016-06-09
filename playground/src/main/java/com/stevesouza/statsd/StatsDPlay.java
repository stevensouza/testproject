/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.statsd;


import com.timgroup.statsd.StatsDClient;
import com.timgroup.statsd.NonBlockingStatsDClient;

/**
  private static final StatsDClient statsd = new NonBlockingStatsDClient("my.prefix", "statsd-host", 8125);

* @author stevesouza
 */
public class StatsDPlay {
  private static final StatsDClient statsdClient = new NonBlockingStatsDClient("stevesouza.playground", "localhost", 8125);

  public static final void main(String[] args) throws InterruptedException {
    // added for loop with sleep as it seemed without them sometimes the metrics weren't added.  Probably 
    // due to lost udp packets
    for (int i=0;i<10;i++) {
        statsdClient.incrementCounter("mycounter");
        statsdClient.recordGaugeValue("myguage", 100);
        statsdClient.recordExecutionTime("mytimer", 25);
        // measures unique values...
        statsdClient.recordSetEvent("myset", "value1");
        statsdClient.recordSetEvent("myset", "value1");
        statsdClient.recordSetEvent("myset", "value2");
        statsdClient.recordSetEvent("myset", "value3");
        Thread.sleep(250);
    }
  }
}
