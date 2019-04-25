package com.stevesouza.jmx;


import com.jamonapi.proxy.MonProxyFactory;
import com.stevesouza.jmx.javamonitor.ExceptionMXBeanImp;
import com.stevesouza.jmx.javamonitor.Log4jMXBean;
import com.stevesouza.jmx.javamonitor.Log4jMXBeanImp;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.HashSet;
import java.util.Set;


public class RunMxBean {
    static final String MXBEAN1 = "sandbox:type=MyTest,name=steve";
    static final String MXBEAN2 = "sandbox:type=MyMXBean";
    static final String MXBEAN3 = "sandbox:type=MyTest,name=souza";
    static final String MYCLASS_MXBEAN = "sandbox:type=MyClass,name=myName";

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

    /**
     * Test invocation of method with parameters and arguments
     */
    public Object invokePrintName() throws Exception {
        Object params[] = {
                "Steve",
                "Souza",
        };

        String signature[] = {String.class.getName(), String.class.getName()};

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

    /**
     * Test invocation with method with no arguments
     */
    public Object invokeSetMemAttr() throws Exception {
        return mBeanServer.invoke(new ObjectName(MXBEAN1), "setMemAttr", null, null);
    }

    /**
     * Alternative and better way of invoking a method of the class is available at runtime.  Simply get the proxy
     */
    public IMyMXBean getMxBean2() throws Exception {
        // note it returns the interface and not the actual class
        IMyMXBean mxbeanProxy = JMX.newMXBeanProxy(mBeanServer, new ObjectName(MXBEAN2), IMyMXBean.class);
        return mxbeanProxy;
    }

    /**
     * The following code plays with jmx.  See inline comments for actions it takes
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 1) create mxBeans
        RunMxBean runMxBean = new RunMxBean();
        runMxBean.registerMxBean(RunMxBean.MXBEAN1, new MyMXBean());
        runMxBean.registerMxBean(RunMxBean.MXBEAN2, new MyMXBean());
        runMxBean.registerMxBean(RunMxBean.MXBEAN3, new MyMXBean());
        runMxBean.registerMxBean(RunMxBean.MYCLASS_MXBEAN, new MyClass());
        runMxBean.registerMxBean(Log4jMXBean.NAME, new Log4jMXBeanImp());
        runMxBean.registerMxBean("sandbox.jamonapi:name=Exceptions", new ExceptionMXBeanImp());
        runMxBean.registerMxBean("sandbox.jamonapi:name=log4j", new Log4jMXBeanImp());
        runMxBean.registerMxBean("sandbox.jamonapi:name=HttpRequests", new Log4jMXBeanImp());

        // 2) Add notification listener to the Garbage collector mxBeans and fire gc to see the results
        Set<ObjectName> mbeans = runMxBean.getGarbageCollectionMbeans();
        JmxNotificationListener listener = new JmxNotificationListener();
        for (ObjectName name : mbeans) {
            ManagementFactory.getPlatformMBeanServer().addNotificationListener(name, listener, null, null);
        }
        System.gc();

        // 3) add notification listener to one of my custom beans.
        listener = new JmxNotificationListener();
        // IMyMXBean has to support Notification (extends or implements a notification class/interface)
        IMyMXBean mxbeanProxy = runMxBean.getMxBean2();
        ManagementFactory.getPlatformMBeanServer().addNotificationListener(new ObjectName(RunMxBean.MXBEAN2), listener, null, null);

        // 4) Trigger a notification event.  To see this in jconsole go to Notifications section for
        // sandbox:type=MyMXBean and subscribe.  Note just change the property in the menu to have this
        // occur.
        mxbeanProxy.setAttrib1("hi mom");

        ExceptionInterface exceptionInterface = (ExceptionInterface) MonProxyFactory.monitor(new ExceptionImp());
        for (int i = 0; i < 5; i++) {
            try {
                // count exception that was thrown
                exceptionInterface.throwException();
            } catch (Exception e) {

            }
        }


        // 5) keep running the program until explicitly exited (go to jconsole and look at it)
        System.out.println("Waiting... go to jconsole/visualvm and look at the created objects");
        while (true) {
            Thread.sleep(10);
        }
    }

}
