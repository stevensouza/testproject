package com.stevesouza.camel.json.jackson;

import com.stevesouza.camel.PersonsName;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

/**
 * Created by stevesouza on 5/19/14.
 */
@Component
public class FileToJsonToPojoRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:/Users/stevesouza/gitrepo/testproject/playground/src/main/resources/data/in?fileName=personsname.json&noop=true")
        .routeId(getClass().getSimpleName())
        .unmarshal().json(JsonLibrary.Jackson, PersonsName.class)
        .log("json file to pojo (unmarshal): messageid=${id}, Person name pojo=${body}");
    }
}
