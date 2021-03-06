package com.stevesouza.camel.json.jackson;

import com.stevesouza.camel.BaseRouteBuilder;
import com.stevesouza.camel.PersonsName;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

/**
 * Created by stevesouza on 5/19/14.
 * <p>
 * convert json file to pojo
 */
@Component
public class FileToJsonToPojoRouteBuilder extends BaseRouteBuilder {

    @Override
    public void configure() throws Exception {
        from(getInput2Dir() + "?fileName=personsname.json&noop=true")
                .routeId(getClass().getSimpleName())
                .unmarshal().json(JsonLibrary.Jackson, PersonsName.class)
                .log("json file to pojo (unmarshal): messageid=${id}, Person name pojo=${body}");
    }

}
