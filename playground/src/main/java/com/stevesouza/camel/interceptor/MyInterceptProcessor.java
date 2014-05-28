package com.stevesouza.camel.interceptor;

import com.jamonapi.MonitorFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by stevesouza on 5/19/14.
 */
public class MyInterceptProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("in intercept processor. id="+exchange.getExchangeId());
        MonitorFactory.add("camelInterceptor: "+getClass().getCanonicalName(), "count", 1);
        //exchange.getProperties();
        //exchange.getIn()
    }
}
