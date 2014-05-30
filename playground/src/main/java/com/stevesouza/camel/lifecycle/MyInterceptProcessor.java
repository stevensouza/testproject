package com.stevesouza.camel.lifecycle;

import com.jamonapi.MonitorFactory;
import com.stevesouza.camel.CamelDriver;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by stevesouza on 5/19/14.
 */
@Component
public class MyInterceptProcessor implements Processor {
    private static final Logger LOG = Logger.getLogger(MyInterceptProcessor.class);

    private String message="";
    public MyInterceptProcessor() {
    }
    public MyInterceptProcessor(String message) {
        this.message = message;
    }
    @Override
    public void process(Exchange exchange) throws Exception {
        LOG.info(message + " id=" + exchange.getExchangeId());
        MonitorFactory.add(message + ".camelInterceptor: " + getClass().getCanonicalName(), "count", 1);
        //exchange.getProperties();
        //exchange.getIn()
    }
}
