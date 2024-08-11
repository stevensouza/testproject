package com.stevesouza.aspectj.logging.automon;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 Tests call, withincode (any pointcut directly in a method), cflow (any pointcuts directly in the method as well as any pointcuts in the
 code that any of the methods call too).
 */
public aspect NativeAspectLoggingAutomon {
    private static final Logger logger = LoggerFactory.getLogger(NativeAspectLoggingAutomon.class);

    private final LogTracingHelper helper = LogTracingHelper.getInstance();

    // Note within does all pointcuts within the class including this and static and constructors etc.
    // this() would not do static methods but only instance ones.
//    pointcut loggingInfo(): execution(* com.stevesouza.aspectj.logging.automon.MyLoggerClass.*(..));
    pointcut loggingInfo(): within(com.stevesouza.aspectj.logging.automon.MyLoggerClass);


    before(): loggingInfo() {
        helper.withKind(thisJoinPointStaticPart).
                withSignature(thisJoinPointStaticPart).
                withParameters(thisJoinPoint).
                withThis(thisJoinPoint);
        // for call logging not tracing
        helper.withEnclosingSignature(thisEnclosingJoinPointStaticPart).
        withTarget(thisJoinPoint);
        logger.info("BEFORE");
    }

    after(): loggingInfo() {
        helper.removeParameters().
                withExecutionTime((int) Math.random() * 1000);
        logger.info("AFTER");
        helper.removeExecutionTime()
                .removeSignature()
                .removeEnclosingSignature()
                .removeKind()
                .removeTarget()
                .removeThis();
    }
}
