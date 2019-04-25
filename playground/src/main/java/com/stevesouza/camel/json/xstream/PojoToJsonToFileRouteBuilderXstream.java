package com.stevesouza.camel.json.xstream;

import com.stevesouza.camel.BaseRouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by stevesouza on 5/19/14.
 * <p>
 * convert pojo to json using xstream and write it to a file
 */
@Component
public class PojoToJsonToFileRouteBuilderXstream extends BaseRouteBuilder {


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
                .to(getOutputDir() + "?fileName=personsnamexstream_pojotojson.json");
    }
}
