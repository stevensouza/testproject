package com.stevesouza.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
/**
 * Created by stevesouza on 5/19/14.
 */
public class PojoToJsonToFileRouteBuilder extends RouteBuilder  {


    @Override
    public void configure() throws Exception {
      /* You have full control over jackson and can maniuplate the JacksonDataFormat with the following:
        JacksonDataFormat format = new JacksonDataFormat();

        Using xstream default json library the syntax is simpler.  And it also does xml too.
         marshal().json().
        */
        from("direct:personsname")
               // marshal().json().
          .marshal().json(JsonLibrary.Jackson, PersonsName.class)
          .log("from pojo to json: messageid=${id}, Person name as json=${body}")
          .to("file:/Users/stevesouza/gitrepo/testproject/playground/src/main/resources/data/out/?fileName=personsname.json");
    }
}
