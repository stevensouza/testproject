package com.stevesouza.aspectj.logging.automon.all;


import com.stevesouza.aspectj.logging.automon.LogTracingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public aspect TracingAllAspect {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass().getName());
    protected static final String AFTER = "AFTER";
    protected static final String BEFORE = "BEFORE";
    protected final LogTracingHelper helper = LogTracingHelper.getInstance();

    pointcut trace() : within(MyLoggerClassAll);;

    Object around() : trace() {
            helper.withFullContext(thisJoinPoint, thisJoinPointStaticPart, thisEnclosingJoinPointStaticPart);
            LOGGER.info(BEFORE);

            long startTime = System.currentTimeMillis();
            Object returnValue =  proceed();
            helper.withExecutionTime(System.currentTimeMillis() - startTime);
            helper.withReturnValue(objectToString(returnValue));

            LOGGER.info(AFTER);
            helper.removeFullContext();

            return returnValue;
    }

    // AfterThrowing advice that executes when any Throwable is thrown
    after() throwing(Throwable throwable) : trace() {
        try (helper) {
            helper.withException(throwable.getClass().getCanonicalName());
            LOGGER.error(AFTER, throwable);
        }
    }

    protected String objectToString(Object obj) {
        return obj == null ? "null" : obj.toString();
    }


}
