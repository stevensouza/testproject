package com.stevesouza.jmx;

/** Note the the interface needs to end in MXBean. And MyClass implements IMyMXBean.
 * alternatively @MXBean() annotation can be used. */
public interface IMyMXBean {

    public String getAttrib1();

    public void setAttrib1(String attrib1);

    public int getAttrib2();

    public void setAttrib2(int attrib2);
    public String setMemAttr();

    public long getTotalMemory();


    public long getMaxMemory() ;
    public long getFreeMemory();

    public String printName(String fname, String lname);

}

