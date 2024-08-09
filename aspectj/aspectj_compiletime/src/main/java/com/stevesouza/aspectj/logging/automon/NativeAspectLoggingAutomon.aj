package com.stevesouza.aspectj.logging.automon;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 Tests call, withincode (any pointcut directly in a method), cflow (any pointcuts directly in the method as well as any pointcuts in the
 code that any of the methods call too).
 */
public aspect NativeAspectLoggingAutomon {
    private static final Logger logger = LoggerFactory.getLogger(NativeAspectLoggingAutomon.class);

    private final LoggingHelper helper = new LoggingHelper();

    // Note within does all pointcuts within the class including this and static and constructors etc.
    // this() would not do static methods but only instance ones.
//    pointcut loggingInfo(): execution(* com.stevesouza.aspectj.logging.automon.MyLoggerClass.*(..));
    pointcut loggingInfo(): within(com.stevesouza.aspectj.logging.automon.MyLoggerClass);


    before(): loggingInfo() {
        helper.putKind(thisJoinPointStaticPart);
        helper.putSignature(thisJoinPointStaticPart);
        // for call logging not tracing
        helper.putEnclosingSignature(thisEnclosingJoinPointStaticPart);
        helper.putParameters(thisJoinPoint);
        // use for call only - logging not tracing
        helper.putTarget(thisJoinPoint);
        helper.putThis(thisJoinPoint);
        logger.info("BEFORE");
    }

    after(): loggingInfo() {
        helper.removeParameters();
        helper.putExecutionTime();
        logger.info("AFTER");
        helper.removeExecutionTime();
        helper.removeSignature();
        helper.removeEnclosingSignature();
        helper.removeTarget();
        helper.removeThis();
    }
}
