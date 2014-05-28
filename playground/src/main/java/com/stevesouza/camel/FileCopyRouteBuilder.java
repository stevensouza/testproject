package com.stevesouza.camel;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.statements.SpringRepeat;

import javax.inject.Inject;
import javax.inject.Named;


/* Camel route builder that copies files from one directory to another and leaves original file in place
 */
@Component
public class FileCopyRouteBuilder extends SpringRouteBuilder {

    // Note the only way Spring aop will work is if you use spring to create the bean.
    // If you create with 'new MessagePeeker()' spring doesn't manage the object and so
    // advice won't be applied to the object.
    @Inject
    private Processor messagePeeker;

    @Override
    public void configure() throws Exception {
        from("file:/Users/stevesouza/gitrepo/testproject/playground/src/main/resources/data/in?noop=true").
                routeId(getClass().getSimpleName()).
                setHeader("myheader", constant("my first header value")).
                log("messageid=${id}, myheader=${headers.myheader}, allheaders=${headers}").
               //marshal().json(JsonLibrary.Jackson).
                process(messagePeeker).
                to("file:/Users/stevesouza/gitrepo/testproject/playground/src/main/resources/data/out").
                log("messageid=${id}, myheader=${headers.myheader}, allheaders=${in.headers}");
    }
}