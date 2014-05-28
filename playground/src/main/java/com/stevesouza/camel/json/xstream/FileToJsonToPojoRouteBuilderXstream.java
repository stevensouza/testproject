package com.stevesouza.camel.json.xstream;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by stevesouza on 5/19/14.
 */
@Component
public class FileToJsonToPojoRouteBuilderXstream extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {
        // jackson at least by default required PersonsName.class whereas xstream doesn't (class name is in the json)
        from("file:/Users/stevesouza/gitrepo/testproject/playground/src/main/resources/data/in?fileName=personsnamexstream.json&noop=true")
        .routeId(getClass().getSimpleName())
        .unmarshal().json()
          .log("json file to pojo (unmarshal): messageid=${id}, Person name pojo=${body}");
    }
}
