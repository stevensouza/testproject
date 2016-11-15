package com.stevesouza.camel;

import org.springframework.stereotype.Component;


/* Could also use
    beanRef("messagePeekerBean") // spring bean id
    bean(MessagePeekerBean.class) // doesn't have to be a spring bean.
    beanRef("messagePeekerBean", "method") // if not annotated or only possible match.
 */
@Component
public class MessageUsingBeanRouteBuilder extends BaseRouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:messageusingbean").
                routeId(getClass().getSimpleName()). // Note if you don't do this the route would be called route1 etc.
                beanRef("messagePeekerBean"). // changes content of message and returns it as a string to be written to a file
        to(getOutputDir()+"?fileName=${headers.filename}");
    }
}
