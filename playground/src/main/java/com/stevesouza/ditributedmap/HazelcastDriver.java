package com.stevesouza.ditributedmap;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.jamonapi.MonKey;
import com.jamonapi.MonitorComposite;
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
 *
 * From HazelCast documentation:
 *
 * get
 *
 *  V get(Object key)
 * Returns the value for the specified key, or null if this map does not contain this key.
 * Warning:
 * This method returns a clone of original value, modifying the returned value does not change the actual value in the map. One should put modified value back to make changes visible to all nodes.
 *
 * V value = map.get(key);
 * value.updateSomeProperty();
 * map.put(key, value);
 *
 * Warning-2:
 *
 * This method uses hashCode and equals of binary form of the key, not the actual implementations of hashCode and equals defined in key's class.
 */
public class HazelcastDriver {
    // could be Map if we don't want the instance methods of hazelcast
    private IMap map;
    private HazelcastInstance hazelCastInstance = Hazelcast.newHazelcastInstance();

    public HazelcastDriver() {
        map = hazelCastInstance.getMap("MyDistributedMap");
   //     MonitorFactory.setMap(map);
    }

    public static void main(String[] args) throws InterruptedException {
        HazelcastDriver driver = new HazelcastDriver();
        String nodeName = driver.hazelCastInstance.getCluster().getLocalMember().toString();
        int i=0;
        while (true) {
            i++;
            MonitorFactory.add(args[0] + "-" + i, "count", i);
            TimeUnit.SECONDS.sleep(1);
            if (i%10==0) {
                driver.map.put(nodeName, MonitorFactory.getRootMonitor());
                MonitorComposite composite =  (MonitorComposite) driver.map.get(nodeName);
                System.out.println("****distributed mapsize: " + driver.map.size() + ", MonitorComposite rows: " + composite.getNumRows());
                System.out.println("**** cluster members: "+driver.hazelCastInstance.getCluster().getMembers());
            }
        }
    }
}
