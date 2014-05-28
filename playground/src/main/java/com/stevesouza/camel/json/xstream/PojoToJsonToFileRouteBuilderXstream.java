package com.stevesouza.camel.json.xstream;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by stevesouza on 5/19/14.
 */
@Component
public class PojoToJsonToFileRouteBuilderXstream extends SpringRouteBuilder  {


    @Override
    public void configure() throws Exception {
        // intercept called 3 times.  Starting from after 'from' and ending right before 'to'
       /* You have full control over jackson and can maniuplate the JacksonDataFormat with the following:
        JacksonDataFormat format = new JacksonDataFormat();

        Using xstream default json library the syntax is simpler.  And it also does xml too.
         marshal().json().
        */
        from("direct:personsname_xstream")
          .routeId(getClass().getSimpleName())
          // marshal().json(). // to use xstream default json.
          // Can also use JsonLibrary.gSon
          // 1) calls intercept
          .marshal().json()
          // 2) calls intercept
          .log("from pojo to json: messageid=${id}, Person name as json=${body}")
          // 3) calls intercept
          .to("file:/Users/stevesouza/gitrepo/testproject/playground/src/main/resources/data/out/?fileName=personsnamexstream.json");
    }
}
