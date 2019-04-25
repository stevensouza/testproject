package com.stevesouza.hazelcast;

import com.fdsapi.ResultSetConverter;
import com.hazelcast.core.*;
import com.hazelcast.map.AbstractEntryProcessor;
import com.jamonapi.MonitorComposite;
import com.jamonapi.MonitorFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by stevesouza on 7/2/14.
 * execute with maven. run 2 instance and pass different arguments.  you will see that their maps have the same replicated
 * data.  hazelcast isn't cheap.  free for 2 nodes and after that a couple thousand dollars per node.
 * <p>
 * mvn exec:java -Dexec.mainClass="com.stevesouza.hazelcast.HazelcastDriver" -Dexec.args="arg1"
 * mvn exec:java -Dexec.mainClass="com.stevesouza.hazelcast.HazelcastDriver" -Dexec.args="zarg2"
 * <p>
 * From HazelCast documentation:
 * <p>
 * get
 * <p>
 * V get(Object key)
 * Returns the value for the specified key, or null if this map does not contain this key.
 * Warning:
 * This method returns a clone of original value, modifying the returned value does not change the actual value in the map. One should put modified value back to make changes visible to all nodes.
 * <p>
 * V value = map.get(key);
 * value.updateSomeProperty();
 * map.put(key, value);
 * <p>
 * Warning-2:
 * <p>
 * This method uses hashCode and equals of binary form of the key, not the actual implementations of hashCode and equals defined in key's class.
 */
public class HazelcastDriver {
    public static final String MAP_NAME = "com.jamonapi";
    // could be Map if we don't want the instance methods of hazelcast
    private IMap<String, MonitorComposite> map;
    private HazelcastInstance hazelCastInstance = Hazelcast.newHazelcastInstance();


    public HazelcastDriver() {
        map = hazelCastInstance.getMap(MAP_NAME);
    }


    public IMap<String, MonitorComposite> getMap() {
        return map;
    }


    public void put(String key, MonitorComposite monitorComposite) {
        map.set(key, monitorComposite);
    }

    public MonitorComposite get(String key) {
        return map.get(key);
    }

    public static void main(String[] args) throws InterruptedException {
        HazelcastDriver driver = new HazelcastDriver();
        String nodeName = driver.hazelCastInstance.getCluster().getLocalMember().toString();
        IExecutorService executorService = driver.hazelCastInstance.getExecutorService("my-distributed-executor");
        int i = 0;
        while (true) {
            i++;
            MonitorFactory.add(args[0] + "-" + i, "count", i);
            MonitorFactory.add("steve", "count", 1);
            TimeUnit.SECONDS.sleep(1);
            if (i % 20 == 0) {
                driver.put(nodeName, MonitorFactory.getRootMonitor());
                MonitorComposite composite = driver.get(nodeName);
                // go to key owner and execute code.  this makes it so data doesn't have to cross the network.
                executorService.executeOnKeyOwner(new MessagePrinter(nodeName), nodeName);
                // The same concept as above but a different approach. It uses EntryProcessors to do the trick.  Will
                // probably use this approach in jamon to run arraysql against the data.
                Map<String, Object> map = driver.getMap().executeOnKeys(new TreeSet<String>(driver.getMap().keySet()), new DistributedJamonFilter(nodeName, "select * from array"));
                System.out.println("entryProcessor return map size: " + map.size() + ", map=" + map);
                System.out.println("nodeName:  " + nodeName + ", MonitorComposite rows: " + composite.getNumRows());
                System.out.println("rows in jamon map: " + driver.getMap().size());
                System.out.println();
            }
        }
    }


    // execute this code on each member in the cluster
    // Note Callable allows you to return a value.
    static class MessagePrinter implements Runnable, Serializable, HazelcastInstanceAware {
        private static final long serialVersionUID = 279L;
        public static final String MAP_NAME = "com.jamonapi";

        private final String key;// nodename
        private transient HazelcastInstance hazelcastInstance;
        private String string;

        MessagePrinter(String key) {
            this.key = key;
        }

        @Override
        public void run() {
            Map<String, MonitorComposite> map = hazelcastInstance.getMap(MAP_NAME);
            MonitorComposite monitorComposite = map.get(key);
            string = monitorComposite.toString();
            //Hazelcast.getAllHazelcastInstances();
            ResultSetConverter rsc = new ResultSetConverter(monitorComposite.getHeader(), monitorComposite.getData());
            rsc = rsc.execute("select * from array");
        }

        public String getData() {
            return string;
        }

        @Override
        public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
            this.hazelcastInstance = hazelcastInstance;
        }
    }

    // EntryProcessors go to the jvm that owns the key and executes the code locally.  So it covers data locality
    // much like hadoop.
    private static class DistributedJamonFilter extends AbstractEntryProcessor<String, MonitorComposite> implements Serializable {
        private static final long serialVersionUID = 279L;

        private static AtomicInteger idGenerator = new AtomicInteger();
        private int id;
        private String generatingNode;
        private String arraySql;


        public DistributedJamonFilter(String generatingNode, String arraySql) {
            id = idGenerator.incrementAndGet();
            this.generatingNode = generatingNode;
            this.arraySql = arraySql;
        }

        @Override
        public Object process(Map.Entry<String, MonitorComposite> entry) {
            String key = entry.getKey();
            MonitorComposite monitorComposite = entry.getValue();
            if (monitorComposite != null) {
                System.out.println("EntryProcessor key=" + key + ", id=" + id + ", monitorComposite rows = " + monitorComposite.getNumRows() + ", generatingNode=" + generatingNode);
            } else {
                System.out.println("EntryProcessor key=" + key + ", id=" + id + ",  monitorComposite rows = NO DATA" + ", generatingNode=" + generatingNode);
            }

            // Just executing this as a placeholder for the logic that will go in jamon.
            // It is currently a noop.
            ResultSetConverter rsc = new ResultSetConverter(monitorComposite.getHeader(), monitorComposite.getData()).execute(arraySql);

            return monitorComposite;
        }
    }


}
