package com.stevesouza.ditributedmap;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.jamonapi.MonKey;
import com.jamonapi.MonitorFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by stevesouza on 7/2/14.
 * execute with maven. run 2 instance and pass different arguments.  you will see that their maps have the same replicated
 * data.  hazelcast isn't cheap.  free for 2 nodes and after that a couple thousand dollars per node.
 *
 * mvn exec:java -Dexec.mainClass="com.stevesouza.ditributedmap.HazelcastDriver" -Dexec.args="arg1"
 * mvn exec:java -Dexec.mainClass="com.stevesouza.ditributedmap.HazelcastDriver" -Dexec.args="zarg2"
 */
public class HazelcastDriver {
    // could be Map if we don't want the instance methods of hazelcast
    private IMap map;

    public HazelcastDriver() {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        map = instance.getMap("MyDistributedMap");
   //     MonitorFactory.setMap(map);
    }

    public static void main(String[] args) throws InterruptedException {
        HazelcastDriver driver = new HazelcastDriver();
        int i=0;
        while (true) {
            i++;
            MonitorFactory.add(args[0] + "-" + i, "count", i);
           // driver.map.putIfAbsent(args[0] + "-" + i, "myvalue" + i);
            TimeUnit.SECONDS.sleep(1);
            if (i%10==0) {
                Set<Map.Entry<Object, Object>> set = MonitorFactory.getMap().entrySet();
                Iterator iter = set.iterator();
                while (iter.hasNext()) {
                    Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iter.next();
                    driver.map.putIfAbsent(entry.getKey().toString(), entry.getValue().toString());
                }
                System.out.println("****distributed mapsize: " + driver.map.size() + ", values: " + new TreeMap(driver.map));
            }
        }
    }
}
