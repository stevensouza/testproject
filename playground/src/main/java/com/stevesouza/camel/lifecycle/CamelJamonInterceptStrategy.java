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
 * see p 379 of camel in action.  note this is picked up as an intercept strategy as
 * part of the spring.xml file. It is defined as a bean in there and automatically loaded
 * into the Camel context.  you could manually do this by  adding the interceptor to the
 * camel context: context.addInterceptStrategy(new MyInterceptor());
 * <p>
 * <p>
 * <p>
 * The effect of an interceptStrategy on the route follows:
 * normal route:  consumer->processor1->processor2->producer
 * intercepted route: consumer->interpector(proessor1)->interceptor(processor2)->producer
 */
//@Component this could be defined as a component instead of in the spring context.xml however
// i think that is a bit more mysterious.
public class CamelJamonInterceptStrategy implements InterceptStrategy {
    private static final Logger LOG = Logger.getLogger(CamelJamonInterceptStrategy.class);


    @Override
    public Processor wrapProcessorInInterceptors(
            CamelContext context,
            ProcessorDefinition<?> definition,
            final Processor target,
            Processor nextTarget) throws Exception {


        Processor processor = new Processor() {
            public void process(Exchange exchange) throws Exception {
                // I don't use jamon, but presumably could
                LOG.info("* Start interceptor - " + target.getClass());
                target.process(exchange);
                LOG.info("* End interceptor");

            }

        };

        return new DelegateAsyncProcessor(processor); // not clear why the processor is wrapped in this but it seems to be standard.
    }
}
