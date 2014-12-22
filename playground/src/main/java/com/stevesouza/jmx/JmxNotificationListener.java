package com.stevesouza.jmx;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;

/**
 * Class that can listen to any jmx notifications.  It is a good place to see how gc notifications work. I also register and
 * send my own notification.  See MyMXBean.setAttrib1(...)
 */
public class JmxNotificationListener implements NotificationListener {
    private String message="";

    public void handleNotification(Notification notification, Object handback) {
        System.out.println("jmx Notification: "+notification);
        System.out.println("jmx Notification handback: "+handback);
        String notifyType = notification.getType();
        if (notifyType.equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            // retrieve the garbage collection notification information
            CompositeData cd = (CompositeData) notification.getUserData();
            GarbageCollectionNotificationInfo gcNotifyInfo = GarbageCollectionNotificationInfo.from(cd);
            print(gcNotifyInfo);
        }
        message="Message is: "+notification;


    }

    private void print(GarbageCollectionNotificationInfo gcNotifyInfo) {
        GcInfo gcInfo = gcNotifyInfo.getGcInfo();
        System.out.println("GarbageCollectionNotificationInfo:");
        System.out.println("  getGcAction:"+gcNotifyInfo.getGcAction());
        System.out.println("  getGcCause:"+gcNotifyInfo.getGcCause());
        System.out.println("  getGcName:"+gcNotifyInfo.getGcName());
        System.out.println("  gcInfo:");

        // http://docs.oracle.com/javase/7/docs/jre/api/management/extension/com/sun/management/GcInfo.html
        // count times fired for each type, duration for each type, and delta between firings for each type.
        System.out.println("    gcInfo.getStartTime:"+gcInfo.getStartTime()); // ms since server started
        System.out.println("    gcInfo.getEndTime:"+gcInfo.getEndTime());  // ms since server started
        System.out.println("    gcInfo.getDuration (ms):"+gcInfo.getDuration()); // simple math of above
        System.out.println("    gcInfo.getId:"+gcInfo.getId()); // number of times this collector has fired since startup
        // probably for jamon use the following for each of the following maps.  not sure what yet, but used looks good and maybe deltas
        // http://docs.oracle.com/javase/7/docs/api/java/lang/management/MemoryUsage.html
        System.out.println("    gcInfo.getMemoryUsageBeforeGc:"+gcInfo.getMemoryUsageBeforeGc()); // Map<String,MemoryUsage>
        System.out.println("    gcInfo.getMemoryUsageAfterGc:"+gcInfo.getMemoryUsageAfterGc()); // Map<String,MemoryUsage>
        System.out.println("    gcInfo.values:"+gcInfo.values());

    }

    public String getMessage() {
        return message;
    }

    public void resetMessage() {
        message="";
    }

}
