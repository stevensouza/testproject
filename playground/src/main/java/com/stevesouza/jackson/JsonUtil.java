package com.stevesouza.jackson;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;

/**
 * Created by stevesouza on 4/25/14.
 */
public class JsonUtil {

    public static String toJsonString(Object pojo) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        String json = null;
        try {
            json = mapper.writeValueAsString(pojo);
        } catch (IOException e) {
            throw new RuntimeException("Unable to convert the following object to json: "+pojo, e);
        }
        return json;
    }

    public static MyPojo toObject(String json, Class klazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MyPojo pojo = mapper.readValue(json, MyPojo.class);
        return pojo;
    }
    // convert SandboxReport to a map excluding any fields that have null values.
    //private Map<String, Object> toMap(SandboxReport behavior)  {
       // Map<String,Object> map = null;
    //        try {
//    String json = mapper.writeValueAsString(behavior);
//    map = mapper.readValue(json,  new TypeReference<HashMap<String,String>>(){});
//} catch (IOException e) {
//        throw new RuntimeException("Unable to convert the following object to json: "+behavior, e);
//        }
//        return map;



}
