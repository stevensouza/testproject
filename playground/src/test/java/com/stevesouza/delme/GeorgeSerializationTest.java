/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.delme;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.junit.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author stevesouza
 */
public class GeorgeSerializationTest {

    protected static void monitorSomething() throws InterruptedException, IOException, ClassNotFoundException{
        Monitor outerMonitor = MonitorFactory.start("outerMonitor");
        for (int i=1; i<=10; i++) {
            Monitor inner = MonitorFactory.start("myInnerMonitor");
            Monitor serializedMonitorSource = MonitorFactory.start("serializedMonitor");
            Thread.sleep(100+i);
            byte[] serialized = serialize( serializedMonitorSource );
            Monitor serializedMonitorDestination = (Monitor)deserialize( serialized );
            serializedMonitorDestination.stop();
            inner.stop();
        }
        outerMonitor.stop();
        MonitorFactory.start("mySecondMonitor")
                      .stop();

    }

    protected static void printPerformanceMeasurements(){
        for( Object monitor: MonitorFactory.getMap().values() ){
            System.out.println( monitor );  
        }

    }

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        monitorSomething();
        printPerformanceMeasurements();
    }

    private static byte[] serialize(Serializable serializable)  throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(serializable);
            byte[] messageBytes = bos.toByteArray();
            return messageBytes;
        }

    }

    private static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException{
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInput in = new ObjectInputStream(bis)) {
            return in.readObject();
        }         
    }
}
