package com.stevesouza.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Standard mbeans force you to know your interface at design time.  DynamicMBeans allow you to know the keys that you
 * want to display (i.e. min, max, stdev) at initialization time.  This is useful for jamon and gc as I don't know the jamon
 * gc keys until runtime when the gc has fired.  Note it doesn't appear that I can add keys to the dynamicmbean after
 * it was first registered in jmx (main method).  This might be useful in the case where when it is initialized all
 * jamon keys aren't known.
 */
public class MyDynamicMBean implements DynamicMBean {
    private int counter;
    private MBeanInfo mBeanInfo;

    public MyDynamicMBean() {
        initMbeanInfo();
    }

    // for a key like hello_attrib1 return hello_attrib1_value10 (depending on current counter value)
    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        MBeanAttributeInfo[] attributes = mBeanInfo.getAttributes();
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].getName().equalsIgnoreCase(attribute)) {
                return attributes[i].getName() + "_value" + (counter++);
            }
        }

        throw new AttributeNotFoundException("No such attribute: " + attribute);
    }

    // not allowed
    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
        throw new AttributeNotFoundException("setAttribute is not valid for " + this.getClass().getName() + " (attribute=" + attribute + ")");
    }

    // invalid values are ignored.
    @Override
    public AttributeList getAttributes(String[] attributes) {
        AttributeList list = new AttributeList();
        for (String name : attributes) {
            try {
                list.add(new Attribute(name, getAttribute(name)));
            } catch (Exception e) {
                // ignore
            }
        }
        return list;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        MBeanAttributeInfo[] attributeInfo = mBeanInfo.getAttributes();
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < attributeInfo.length; i++) {
            list.add(attributeInfo[i].getName());
        }

        return getAttributes(list.toArray(new String[0]));
    }

    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
        throw new ReflectionException(new NoSuchMethodException("invoke is not valid for " + this.getClass().getName() + " (actionName=" + actionName + ")"));
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        return mBeanInfo;
    }

    private void initMbeanInfo() {
        MBeanAttributeInfo[] attrs = {
                new MBeanAttributeInfo(
                        "hello_attrib1",
                        "java.lang.String",
                        "My description",
                        true,   // isReadable
                        false,   // isWritable
                        false),
                new MBeanAttributeInfo(
                        "hello_attrib2",
                        "java.lang.String",
                        "My description2",
                        true,   // isReadable
                        false,   // isWritable
                        false)
        };

        mBeanInfo = new MBeanInfo(
                this.getClass().getName(),
                "GcInfo Bean",
                attrs,
                null,   // constructors
                null,   // operations
                null);  // notifications

    }

    private static void registerMxBean(String objectName, Object mxbean) throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName mxbeanName = new ObjectName(objectName);
        mBeanServer.registerMBean(mxbean, mxbeanName);
    }


    public static void main(String[] args) throws Exception {
        // Register the dynamic mbean
        String name = "sandbox.gc:name=MyDynamicMBean";
        registerMxBean(name, new MyDynamicMBean());
        Thread.sleep(10000000);
    }

}
