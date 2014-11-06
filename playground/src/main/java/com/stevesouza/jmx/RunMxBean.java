package com.stevesouza.jmx;


import java.lang.management.ManagementFactory;
import java.util.HashSet;
import java.util.Set;

import javax.management.*;


public class RunMxBean {
     static final String MXBEAN1="sandbox:type=MyTest,name=steve";
     static final String MXBEAN2="sandbox:type=MyMXBean";
     static final String MXBEAN3="sandbox:type=MyTest,name=souza";
     static final String MYCLASS_MXBEAN="sandbox:type=MyClass,name=myName";

    // use this when you are getting mbeans in the same jvm the server executes. Alternatively you can get a remote
    // connection.
    private MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();


    public void registerMxBean(String objectName, Object mxbean) throws Exception {
        ObjectName mxbeanName = new ObjectName(objectName);
        mBeanServer.registerMBean(mxbean, mxbeanName);
    }

    public void unregisterMxBean(String objectName) throws Exception {
        ObjectName mxbeanName = new ObjectName(objectName);
        mBeanServer.unregisterMBean(mxbeanName);
    }

    /** Test invocation of method with parameters and arguments */
    public Object invokePrintName() throws  Exception{
        Object  params[] = {
                "Steve",
                "Souza",
        };

        String  signature[] = { String.class.getName(), String.class.getName()};

        return mBeanServer.invoke(new ObjectName(MXBEAN1), "printName", params, signature);
    }

    public Set<ObjectName> getGarbageCollectionMbeans() throws Exception {
        Set<ObjectName> mbeans = ManagementFactory.getPlatformMBeanServer().queryNames(null, null);
        Set<ObjectName> gcMbeans = new HashSet<ObjectName>();

        for (ObjectName objectInstance : mbeans) {
            if (objectInstance.toString().contains("type=GarbageCollector")) {
                gcMbeans.add(objectInstance);
            }
        }
        return gcMbeans;
    }

    /** Test invocation with method with no arguments */
    public Object invokeSetMemAttr() throws  Exception{
        return mBeanServer.invoke(new ObjectName(MXBEAN1), "setMemAttr", null, null);
    }

    /** Alternative and better way of invoking a method of the class is available at runtime.  Simply get the proxy */
    public IMyMXBean getMxBean2() throws Exception {
        // note it returns the interface and not the actual class
        IMyMXBean mxbeanProxy = JMX.newMXBeanProxy(mBeanServer, new ObjectName(MXBEAN2),  IMyMXBean.class);
        return mxbeanProxy;
    }

}
