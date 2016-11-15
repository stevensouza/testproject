package com.stevesouza.camel.json.jackson;

import com.stevesouza.camel.BaseRouteBuilder;
import com.stevesouza.camel.PersonsName;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

/**
 * Created by stevesouza on 5/19/14.
 * 
 * convert pojo to json and write to a file
 */
@Component
public class PojoToJsonToFileRouteBuilder extends BaseRouteBuilder {


    @Override
    public void configure() throws Exception {
        // intercept called 3 times.  Starting from after 'from' and ending right before 'to'
//        intercept().process(new MyInterceptProcessor("intercept"));
//        interceptFrom().process(new MyInterceptProcessor("interceptFrom"));

       /* You have full control over jackson and can maniuplate the JacksonDataFormat with the following:
        JacksonDataFormat format = new JacksonDataFormat();

        Using xstream default json library the syntax is simpler.  And it also does xml too.
         marshal().json().
        */
        from("direct:personsname")
          .routeId(getClass().getSimpleName())
          // marshal().json(). // to use xstream default json.
          // Can also use JsonLibrary.gSon
          // 1) calls intercept
          .marshal().json(JsonLibrary.Jackson, PersonsName.class)
          // 2) calls intercept
          .log("from pojo to json: messageid=${id}, Person name as json=${body}")
          // 3) calls intercept
          .to(getOutputDir()+"?fileName=personsname_pojotojson.json");
    }
}
