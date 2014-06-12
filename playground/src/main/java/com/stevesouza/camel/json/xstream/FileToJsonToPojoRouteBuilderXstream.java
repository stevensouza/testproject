package com.stevesouza.camel.json.xstream;

import com.stevesouza.camel.BaseRouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by stevesouza on 5/19/14.
 */
@Component
public class FileToJsonToPojoRouteBuilderXstream extends BaseRouteBuilder {

    @Override
    public void configure() throws Exception {
        // jackson at least by default required PersonsName.class whereas xstream doesn't (class name is in the json)
        from(getInput2Dir()+"?fileName=personsnamexstream.json&noop=true")
        .routeId(getClass().getSimpleName())
        .unmarshal().json()
        .log("json file to pojo (unmarshal): messageid=${id}, Person name pojo=${body}");
    }
}
