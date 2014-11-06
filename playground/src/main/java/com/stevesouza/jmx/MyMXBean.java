package com.stevesouza.jmx;

import java.util.Map;
import java.util.TreeMap;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;


/** Must extend NotificationBroadcasterSupport, or implement NotificationEmitter if it supports notifications */
public class MyMXBean  extends NotificationBroadcasterSupport implements IMyMXBean {

    private String attrib1="";
    private int attrib2;
    private long notificationSequenceNumber;

    public String getAttrib1() {
        return attrib1;
    }

    /** Note this sends a notification when called */
    public void setAttrib1(String attrib1) {
        String message = "Changing from:"  + this.attrib1 + ", to:" +  attrib1;
        System.out.println(message);
        // http://docs.oracle.com/javase/1.5.0/docs/api/javax/management/AttributeChangeNotification.html#AttributeChangeNotification(java.lang.Object, long, long, java.lang.String, java.lang.String, java.lang.String, java.lang.Object, java.lang.Object)
        Notification notification = new AttributeChangeNotification(
                this, // source
                notificationSequenceNumber++, // sequenceNumber
                System.currentTimeMillis(), // timeStamp
                message,
                "attrib1", // attributeName
                "java.lang.String", // attributeType
                this.attrib1,  // oldValue
                attrib1); // newValue
        this.attrib1 = attrib1;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("mick", "jagger");
        map.put("keith", "richards");
        notification.setUserData(map);
        sendNotification(notification);
    }

    public int getAttrib2() {
        return attrib2;
    }

    public void setAttrib2(int attrib2) {
        System.out.println(this.attrib2 + " - " +  attrib2);
        this.attrib2 = attrib2;
    }

    public long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }


    public long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }


    public long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }



    // private long freeMem, maxMem, totalMem;


    public String setMemAttr() {
        System.out.println("Setting memory");
        return "hello world";
    }

    public String printName(String fname, String lname) {
        String str = "My name is: "+fname+" "+lname;
        System.out.println(str);
        return str;

    }

}
