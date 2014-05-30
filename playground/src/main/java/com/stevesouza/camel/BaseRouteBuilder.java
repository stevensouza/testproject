package com.stevesouza.camel;

import com.stevesouza.camel.lifecycle.MyInterceptProcessor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by stevesouza on 5/29/14.
 */
public abstract class BaseRouteBuilder extends SpringRouteBuilder {
    public BaseRouteBuilder() {
        useInterceptors();
    }

//    @Inject
//    @Named("interceptProcessor")
//    protected MyInterceptProcessor interceptProcessor;

//    @Inject
//    @Named("interceptFromProcessor")
//   protected MyInterceptProcessor interceptFromProcessor;


    protected void useInterceptors() {
        // disabling as it is redundant to all the other monitoring being done.
  //      intercept().process(new MyInterceptProcessor("intercept"));
  //      interceptFrom().process(new MyInterceptProcessor("interceptFrom"));
    }


}
