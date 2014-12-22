package com.stevesouza.jmx.javamonitor;

/**
 * Created by stevesouza on 11/19/14.
 */
public interface ExceptionMXBean {
    public static final String NAME = "sandbox.jamonapi:name=Log4j";

    public String getMostRecentStackTrace();
    public long getExceptions();
}
