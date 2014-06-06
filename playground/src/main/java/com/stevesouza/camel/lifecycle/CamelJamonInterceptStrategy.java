package com.stevesouza.camel.lifecycle;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.processor.DelegateAsyncProcessor;
import org.apache.camel.spi.InterceptStrategy;
import org.apache.log4j.Logger;

/**
 * Created by stevesouza on 5/29/14.
 */
//@Component this could be defined as a component instead of in the spring context.xml however
// i think that is a bit more mysterious.
public class CamelJamonInterceptStrategy implements InterceptStrategy {
    private static final Logger LOG = Logger.getLogger(CamelJamonInterceptStrategy.class);


    @Override
    public Processor wrapProcessorInInterceptors(CamelContext context, ProcessorDefinition<?> definition,
           final Processor target, Processor nextTarget) throws Exception {

        Processor processor = new Processor() {
            public void process(Exchange exchange) throws Exception {
                LOG.info("* Start interceptor - " + target.getClass());
                target.process(exchange);
                LOG.info("* End interceptor");

            }

        };

        return new DelegateAsyncProcessor(processor);
    }
}
