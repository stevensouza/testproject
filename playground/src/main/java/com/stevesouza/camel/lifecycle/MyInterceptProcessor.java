package com.stevesouza.camel.lifecycle;

import com.jamonapi.MonitorFactory;
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

    private int counter=0;
    private String message="";
    public MyInterceptProcessor() {
    }
    public MyInterceptProcessor(String message) {
        this.message = message;
    }
    @Override
    public void process(Exchange exchange) throws Exception {
        LOG.info("* " + message +" counter="+(counter++) + ", exchangeId=" + exchange.getExchangeId());
        MonitorFactory.add("camel."+message + ": " + getClass().getCanonicalName(), "count", 1);
        // List<MessageHistory> history = exchange.getProperty(Exchange.MESSAGE_HISTORY, List.class);
        // LOG.info("* "+history);
        
        // useful methods
        //  exchange.getProperties(); 
        //  exchange.getIn() - message
    }
}
