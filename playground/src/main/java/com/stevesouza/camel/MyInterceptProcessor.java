package com.stevesouza.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by stevesouza on 5/19/14.
 */
public class MyInterceptProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("****in intercept processor. id="+exchange.getExchangeId());
        //exchange.getProperties();
        //exchange.getIn()
    }
}
