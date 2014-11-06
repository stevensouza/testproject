package com.stevesouza.jmx;

import javax.management.Notification;
import javax.management.NotificationListener;

public class JmxNotificationListener implements NotificationListener {
    private String message="";

    public void handleNotification(Notification notification, Object handback) {
        System.out.println("jmx Notification: "+notification);
        System.out.println("jmx Notification handback: "+handback);
        message="Message is: "+notification;
    }

    public String getMessage() {
        return message;
    }

    public void resetMessage() {
        message="";
    }

}
