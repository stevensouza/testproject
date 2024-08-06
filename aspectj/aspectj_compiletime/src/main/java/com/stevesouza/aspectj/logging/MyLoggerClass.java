package com.stevesouza.aspectj.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.NDC;

public class MyLoggerClass {
    // Disable additivity (additivity="false"), meaning messages won't be passed to the parent logger.
    private static final Logger logger = LoggerFactory.getLogger(MyLoggerClass.class);

    /*
    ndc ideas. each could be a seperate aspect.
       requestId
       userId
       methods (controllers)

   mdc ideas
    method variables.
     */
    private void logMe() {
        MDC.put("username", "Steve Souza");
        // Note slf4j puts NDC in MDC and you can display it in a format with %X (which is MDC) or %X(NDC0)
        // %X would be {NDC0=logMe1, NDC1=logMe2, username=Steve Souza}
        // %X(NDC0) would be logMe1
        NDC.push("logMe1");
        logger.trace("This is a TRACE message (will not print)");
        logger.debug("User {} logged in. (will not print)", "username");  // Parameterized message
        NDC.push("logMe2");
        logger.info("My name is {}.", "steve");
        NDC.pop();
        logger.info("My name is {}.", "souza");
        logger.info("this signature calls aspect");
        logger.warn("My warning message");
        logger.error("My error/exception message. Failed to process request", new Exception("my exception")); // Log with exception
    }

    public static void main(String[] args) {
        MyLoggerClass myLoggerClass = new MyLoggerClass();
        myLoggerClass.logMe();
    }
}
