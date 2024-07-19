package com.stevesouza.aspectj.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class MyLoggerClass {
    // Disable additivity (additivity="false"), meaning messages won't be passed to the parent logger.
    private static final Logger logger = LoggerFactory.getLogger(MyLoggerClass.class);

    private void logMe() {
        MDC.put("username", "Steve Souza");
        logger.trace("This is a TRACE message (will not print)");
        logger.debug("User {} logged in. (will not print)", "username");  // Parameterized message
        logger.info("My name is {}.", "steve");
        logger.warn("My warning message");
        logger.error("My error/exception message. Failed to process request", new Exception("my exception")); // Log with exception

    }
    public static void main(String[] args) {
        MyLoggerClass myLoggerClass = new MyLoggerClass();
        myLoggerClass.logMe();
    }
}
