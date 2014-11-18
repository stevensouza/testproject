package com.stevesouza.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;


/* Camel route builder that throws an exception due to file not existing.

I commented out the autodetection as when this fails it generates an ugly stack
trace.  Note if it is a file directory that is attempted to be copied it keeps trying and so generates many of them.

Here is a sample line that is generated in jamon during one run.

2014-05-30 14:35:12 INFO  Utils:16 - JAMon Label=class org.apache.camel.management.event.ExchangeFailedEvent, Units=count: (LastValue=1.0, Hits=10.0,
  Avg=1.0, Total=10.0, Min=1.0, Max=1.0, Active=0.0, Avg Active=0.0, Max Active=0.0, First Access=Fri May 30 14:35:07 CEST 2014, Last Access=Fri May 30 14:35:11 CEST 2014)
 */
//@Component
public class ExceptionFileCopyRouteBuilder extends BaseRouteBuilder {


    @Override
    public void configure() throws Exception {
        from(getInput2Dir()+"?noop=true").
          process(new MyExceptionProcessor()).
          to(getOutputDir());
    }

    private static class MyExceptionProcessor implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            throw new Exception("throwing my runtime exception to test events");
        }
    }
}
