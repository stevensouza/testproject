package com.stevesouza.camel;

import com.stevesouza.camel.lifecycle.MyInterceptProcessor;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Note if you don't call this in the routeL: routeId(getClass().getSimpleName())
 * the route would be called route1, route2 etc.

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
        // not clear how this relates to all the other forms of intercepting that can occur via Spring and camel.
        intercept().process(new MyInterceptProcessor("intercept"));
        interceptFrom().process(new MyInterceptProcessor("interceptFrom"));
    }


}
