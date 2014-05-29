package com.stevesouza.camel.lifecycle;

import com.jamonapi.MonitorFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by stevesouza on 5/19/14.
 */
public class MyInterceptProcessor implements Processor {
    private String message;

    public MyInterceptProcessor(String message) {
        this.message = message;
    }
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println(message+" id="+exchange.getExchangeId());
        MonitorFactory.add(message + ".camelInterceptor: " + getClass().getCanonicalName(), "count", 1);
        //exchange.getProperties();
        //exchange.getIn()
    }
}
