package com.stevesouza.jackson;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
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



    @Test
    public void shouldConvertJamonToJson() throws IOException {
        Monitor m1 = MonitorFactory.start("m1").stop();
        Monitor m2 = MonitorFactory.start("m2").stop();
        Monitor m3 = MonitorFactory.start("m3").stop();
        Monitor m4 = MonitorFactory.add("m4", "counter", 1);

        String json=JsonUtil.toJsonString(MonitorFactory.getRootMonitor());
        System.out.println(json);
    }
}
