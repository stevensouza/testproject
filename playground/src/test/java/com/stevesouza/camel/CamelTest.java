package com.stevesouza.camel;

import org.apache.camel.EndpointInject;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class CamelTest extends CamelTestSupport {
    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;


    @Test
    public void testSendMatchingMessage() throws Exception {
        String expectedBody = "<matched/>";
        resultEndpoint.expectedBodiesReceived(expectedBody);
        resultEndpoint.expectedHeaderReceived("foo", "bar");
        template.sendBodyAndHeader(expectedBody, "foo", "bar");

        resultEndpoint.assertIsSatisfied();
    }

    @Test
    public void testSendMessageInExchange() throws Exception {
        String expectedBody = "<matched/>";
        Message message = new DefaultMessage();
        message.setBody(expectedBody);
        message.setHeader("foo", "bar");
        DefaultExchange exchange = new DefaultExchange(resultEndpoint);
        exchange.setIn(message);

        resultEndpoint.expectedBodiesReceived(expectedBody);
        resultEndpoint.expectedHeaderReceived("foo", "bar");
        template.send(exchange);

        resultEndpoint.assertIsSatisfied();
    }

    @Test
    public void testSendNotMatchingMessage() throws Exception {
        resultEndpoint.expectedMessageCount(0);
        template.sendBodyAndHeader("<notMatched/>", "foo", "notMatchedHeaderValue");
        resultEndpoint.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:start").filter(header("foo").isEqualTo("bar"))
                        .log("messageid=${id}, myheader=${headers.foo}, allheaders=${headers}")
                        .to("mock:result");
            }
        };
    }
}
