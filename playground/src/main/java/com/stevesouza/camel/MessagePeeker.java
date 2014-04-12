package com.stevesouza.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

/**
 * Class used to log and inspect a message.
 */
public class MessagePeeker  implements Processor {

    private static final Logger LOG = Logger.getLogger(MessagePeeker.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        LOG.info(exchange);
    }
}
