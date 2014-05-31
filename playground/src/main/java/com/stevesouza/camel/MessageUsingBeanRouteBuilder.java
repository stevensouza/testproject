package com.stevesouza.camel;

import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;


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
        beanRef("messagePeekerBean").
        to("file:/Users/stevesouza/gitrepo/testproject/playground/src/main/resources/data/out/?fileName=${headers.filename}");
    }
}