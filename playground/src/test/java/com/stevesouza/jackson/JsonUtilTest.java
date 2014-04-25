package com.stevesouza.jackson;

import org.junit.Test;

import java.io.IOException;

/**
 * Created by stevesouza on 4/25/14.
 */
public class JsonUtilTest {

    @Test
    public void shouldConvertToJson() throws IOException {
        MyPojo pojo=new MyPojo();
        pojo.setAge(22);
        pojo.setFname("steve");
        pojo.setLname("souza");

        String json=JsonUtil.toJsonString(pojo);
        System.out.println(json);

        pojo=JsonUtil.toObject(json, MyPojo.class);
        System.out.println(pojo.getFname());
   
     }
}
