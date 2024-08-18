package com.stevesouza.aspectj.logging.automon.basic;


import com.stevesouza.aspectj.logging.automon.LogTracingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public aspect TracingBasicAspect {
    protected static final Logger LOGGER = LoggerFactory.getLogger(TracingBasicAspect.class);
    protected static final String AFTER = "AFTER";
    protected static final String BEFORE = "BEFORE";
    protected final LogTracingHelper helper = LogTracingHelper.getInstance();

    pointcut trace() : within(MyLoggerClassBasic);

    Object around() : trace() {
            helper.withBasicContext(thisJoinPointStaticPart, thisEnclosingJoinPointStaticPart);
            LOGGER.info(BEFORE);

            long startTime = System.currentTimeMillis();
            Object returnValue =  proceed();
            helper.withExecutionTime(System.currentTimeMillis() - startTime);

            LOGGER.info(AFTER);
            helper.removeBasicContext();

            return returnValue;
    }

    // AfterThrowing advice that executes when any Throwable is thrown
    after() throwing(Throwable throwable) : trace() {
        try (helper) {
            helper.withException(throwable.getClass().getCanonicalName());
            LOGGER.error(AFTER, throwable);
        }
    }


}
