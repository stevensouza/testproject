package com.stevesouza.camel;

import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import javax.inject.Inject;


/* Camel route builder that copies files from one directory to another and leaves original file in place
 */
@Component
public class FileCopyRouteBuilder extends BaseRouteBuilder {

    // Note the only way Spring aop will work is if you use spring to create the bean.
    // If you create with 'new MessagePeeker()' spring doesn't manage the object and so
    // advice won't be applied to the object.
    @Inject
    private Processor messagePeeker;

    @Override
    public void configure() throws Exception {
        from(getInputDir() + "?noop=true").
                routeId(getClass().getSimpleName()).
                setHeader("myheader", constant("my first header value")).
                log("messageid=${id}, myheader=${headers.myheader}, allheaders=${headers}").
                //marshal().json(JsonLibrary.Jackson).
                        process(messagePeeker).
                to(getOutputDir()).
                log("messageid=${id}, myheader=${headers.myheader}, allheaders=${in.headers}");
    }
}
