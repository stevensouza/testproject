package com.stevesouza.camel.lifecycle;

import com.jamonapi.MonKey;
import com.jamonapi.MonKeyImp;
import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import org.apache.camel.management.event.ExchangeFailedEvent;
import org.apache.camel.management.event.ExchangeSentEvent;
import org.apache.camel.support.EventNotifierSupport;

import java.util.EventObject;

/**
 * Monitor the performance of camel routes.
 * Created by stevesouza on 5/29/14.
 */
public class CamelJamonEventNotifier extends EventNotifierSupport {

    // context.getManagementStrategy().addEventNotifier(new SentEventNotifier());
    // or spring.xml which is how I did it.
    private static String BASE_KEY = "camel.route";
    private static String UNITS = "ms.";

    public void notify(EventObject event) throws Exception {
            log.info("event "+event.getClass()+": " + event.toString());

            if (event instanceof ExchangeSentEvent) {
                // sent.toString() is used for the jamon key details.  Its format follows:
                //  ID-Macintosh-c8bcc.. exchange Exchange[Message: <hello>steve!</hello>] sent to: Endpoint[file:///Users/stevesouza...] took: 7 ms.
                //  This would be available form the jamon.war.  Note that looking at the code the message text is limited to 1000 chars, but is also
                //  configurable.
                ExchangeSentEvent sent = (ExchangeSentEvent) event;
                Monitor mon = monitor(sent);
                log.info(mon.toString());
            } else if (event instanceof ExchangeFailedEvent) {
                log.info("failure: "+event);
            }

        }

    private Monitor monitor(ExchangeSentEvent sent) {
        // RouteBuilder class names like FileToJsonToPojoRouteBuilder which inherit from RoutesBuilder
        String sentString = sent.toString();

        // general monitor for all routes
        MonKey key = new MonKeyImp(BASE_KEY+"s", sentString, UNITS);
        MonitorFactory.add(key, sent.getTimeTaken());

        // specific monitor for this sepecific exchange route.
        key = new MonKeyImp(BASE_KEY+"."+sent.getExchange().getFromRouteId(), sentString, UNITS);
        Monitor mon = MonitorFactory.add(key, sent.getTimeTaken());
        return mon;
    }

     public boolean isEnabled(EventObject event) {
        // if we only want the sent events
        // return event instanceof ExchangeSentEvent;
        return true;
     }

     protected void doStart() throws Exception {
       log.info("Starting " + getClass());
     }

     protected void doStop() throws Exception {
       log.info("Stopping " + getClass());
     }

}
