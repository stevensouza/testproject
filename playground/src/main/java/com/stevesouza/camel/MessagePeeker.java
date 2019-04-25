package com.stevesouza.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Class used to log and inspect a message.
 */
@Component
public class MessagePeeker implements Processor {

    private final Logger LOG = Logger.getLogger(getClass());

    @Override
    public void process(Exchange exchange) throws Exception {
        LOG.info(exchange);
    }
}
