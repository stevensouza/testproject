package com.stevesouza.jmx;

/** Note the the inerface needs to end in MXBean. And MyClass implements MyInterfaceMXBean.
 * alternatively @MXBean() annotation can be used. */
public interface MyInterfaceMXBean {
    
    public String getName();
    public void setName(String name);
    public String getPageStats();
    public void generateJamonStats();

}
