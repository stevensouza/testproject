package com.stevesouza.jmx;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.management.ObjectInstance;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RunMxBeanTest {


    private RunMxBean runMxBean = new RunMxBean();
    @Before
    public void setUp() throws Exception {
        runMxBean.registerMxBean(RunMxBean.MXBEAN1, new MyMXBean());
        runMxBean.registerMxBean(RunMxBean.MXBEAN2,  new MyMXBean());
        runMxBean.registerMxBean(RunMxBean.MXBEAN3, new MyMXBean());
        runMxBean.registerMxBean(RunMxBean.MYCLASS_MXBEAN, new MyClass());
    }

    @After
    public void tearDown() throws Exception {
        runMxBean.unregisterMxBean(RunMxBean.MXBEAN1);
        runMxBean.unregisterMxBean(RunMxBean.MXBEAN2);
        runMxBean.unregisterMxBean(RunMxBean.MXBEAN3);
        runMxBean.unregisterMxBean(RunMxBean.MYCLASS_MXBEAN);
    }

    @Test
    public void testPrintName_MethodWithArgs() throws  Exception {
        assertThat(runMxBean.invokePrintName()).isEqualTo("My name is: Steve Souza");
    }

    @Test
    public void testSetMemAttr_MethodWithNoArgs() throws  Exception {
        assertThat(runMxBean.invokeSetMemAttr()).isEqualTo("hello world");
    }

    @Test
    public void testPrintName_usingPreferredProxyMethod() throws  Exception {
        // preferred way to access an mbean or mxbean when you have access to the interface. note the interface being
        // proxied needs to be available.
        IMyMXBean mxbeanProxy = runMxBean.getMxBean2();
        assertThat(mxbeanProxy.printName("jeff", "beck")).isEqualTo("My name is: jeff beck");
    }

    @Test
    public void testQueryMbeans() throws Exception {
        Set<ObjectInstance> mbeans = ManagementFactory.getPlatformMBeanServer().queryMBeans(null, null);
        assertThat(mbeans).isNotEmpty();
        for (ObjectInstance objectInstance : mbeans) {
            System.out.println(objectInstance);
        }
    }

    @Test
    public void testGetGarbageCollectionMbeans() throws Exception {
        Set<ObjectName> mbeans = runMxBean.getGarbageCollectionMbeans();
        assertThat(mbeans).isNotEmpty();
        JmxNotificationListener listener = new JmxNotificationListener();

        for (ObjectName name : mbeans) {
            ManagementFactory.getPlatformMBeanServer().addNotificationListener(name, listener, null, null);
        }
        System.gc();
        Thread.sleep(1000);

        assertThat(listener.getMessage()).contains("Message is:");
        for (ObjectName name : mbeans) {
            ManagementFactory.getPlatformMBeanServer().removeNotificationListener(name, listener, null, null);
        }
        listener.resetMessage();
        System.gc();
        Thread.sleep(1000);

        assertThat(listener.getMessage()).isEmpty();
        // IMyMXBean has to support Notification (extends or implements a notification class/interface)

    }

    @Test
    public void testMbeanCount() throws Exception {
        assertThat(ManagementFactory.getPlatformMBeanServer().getMBeanCount()).isPositive();
    }

    @Test
    public void testPlatformMbeans() throws Exception {
        assertThat(ManagementFactory.getClassLoadingMXBean().getLoadedClassCount()).isPositive();
        assertThat(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed()).isPositive();
        assertThat(ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed()).isPositive();
        assertThat(ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors()).isPositive();
        assertThat(ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage()).isPositive();
        assertThat(ManagementFactory.getGarbageCollectorMXBeans().size()).isPositive();
        assertThat(ManagementFactory.getGarbageCollectorMXBeans().get(0).getCollectionCount()).isNotNegative();
        assertThat(ManagementFactory.getGarbageCollectorMXBeans().get(0).getCollectionTime()).isNotNegative();
        assertThat(ManagementFactory.getThreadMXBean().getThreadCount()).isNotNegative();
    }

    @Test
    public void testNotificationListener() throws Exception {
        JmxNotificationListener listener = new JmxNotificationListener();
        assertThat(listener.getMessage()).isEmpty();

        // IMyMXBean has to support Notification (extends or implements a notification class/interface)
        IMyMXBean mxbeanProxy = runMxBean.getMxBean2();
        ManagementFactory.getPlatformMBeanServer().addNotificationListener(new ObjectName(RunMxBean.MXBEAN2), listener, null, null);

        // This method sends a Notification object.
        mxbeanProxy.setAttrib1("hi mom");
        assertThat(listener.getMessage()).contains("Message is:");

        // remove listener
        ManagementFactory.getPlatformMBeanServer().removeNotificationListener(new ObjectName(RunMxBean.MXBEAN2), listener);
        listener.resetMessage();
        mxbeanProxy.setAttrib1("hi mom");
        assertThat(listener.getMessage()).isEmpty();
    }
    // sun.management.MemoryPoolImpl[java.lang:type=MemoryPool,name=PS Eden Space]
    // java.lang.management.MemoryPoolMXBean

}
