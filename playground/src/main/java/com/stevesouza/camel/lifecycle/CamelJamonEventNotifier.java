package com.stevesouza.camel.lifecycle;

import com.jamonapi.MonKey;
import com.jamonapi.MonKeyImp;
import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import org.apache.camel.management.event.AbstractExchangeEvent;
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

    /** Note first message event when an event is being sent (from) seems to be
     * ExchangeSendingEvent. I could use this to track 'active'. Note for a given route there may be more
     * than one monitor created.  For example from(...).to(...) generates a monitor for the full route and the 'to'
     * route.  It might be good to include the endpoint in the monitor as this allows you to make the distinction.
     * Playing with that in code below.
     *
     * ExchangeSentEvent is event after message is done route (I think only on success so would have to consider this
     * on success)
     * and it comes with a time associated with it.  I use that for jamon.
     * @param event
     * @throws Exception
     */

    public void notify(EventObject event) throws Exception {
            // keep stats for events such as: org.apache.camel.management.event.ExchangeCompletedEvent
            String summaryLabel = event.getClass().toString();
            String details = event.toString();
            MonKey key = new MonKeyImp(summaryLabel, details, "count" );
            MonitorFactory.add(key, 1);
            log.info("event "+summaryLabel+": " + details);


            if (event instanceof ExchangeSentEvent) {
                // sent.toString() is used for the jamon key details.  Its format follows:
                //  ID-Macintosh-c8bcc.. exchange Exchange[Message: <hello>steve!</hello>] sent to: Endpoint[file:///Users/stevesouza...] took: 7 ms.
                //  This would be available form the jamon.war.  Note that looking at the code the message text is limited to 1000 chars, but is also
                //  configurable.
                ExchangeSentEvent sent = (ExchangeSentEvent) event;
                Monitor mon = monitor(details, sent);
                log.info(mon.toString());
            } else if (event instanceof ExchangeFailedEvent) {
                log.info("failure: "+event);
            }

        }

    private Monitor monitor(String details, ExchangeSentEvent sent) {
        // RouteBuilder class names like FileToJsonToPojoRouteBuilder which inherit from RoutesBuilder

        // general monitor for all routes
        MonKey key = new MonKeyImp(BASE_KEY+"s", details, UNITS);
        MonitorFactory.add(key, sent.getTimeTaken());

        // specific monitor for this sepecific exchange route.
        key = new MonKeyImp(BASE_KEY+"."+sent.getExchange().getFromRouteId(), details, UNITS);
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
