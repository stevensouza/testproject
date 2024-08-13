package com.stevesouza.aspectj.logging.automon.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** This class is called from another class to see if MDC/NDC are still valid for it */
public class MyClass {
    private static final Logger logger = LoggerFactory.getLogger(MyClass.class);
    public void otherClassMethod() {
        logger.info("MyClass#otherClassMethod (should have MDC/NDC)");
    }
}
