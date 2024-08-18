package com.stevesouza.aspectj.logging.automon.all;


import com.stevesouza.aspectj.logging.automon.LogTracingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 Tests call, withincode (any pointcut directly in a method), cflow (any pointcuts directly in the method as well as any pointcuts in the
 code that any of the methods call too).
 */
public aspect NativeAspectLoggingAutomonAll {
    private static final Logger logger = LoggerFactory.getLogger(NativeAspectLoggingAutomonAll.class);

    private final LogTracingHelper helper = LogTracingHelper.getInstance();

    // Note within does all pointcuts within the class including this and static and constructors etc.
    // this() would not do static methods but only instance ones.
//    pointcut loggingInfo(): execution(* com.stevesouza.aspectj.logging.automon.MyLoggerClass.*(..));
    pointcut loggingInfo(): within(MyLoggerClassAll);


    before(): loggingInfo() {
        // return value too.
        // for call logging not tracing
        helper.withFullContext(thisJoinPoint, thisJoinPointStaticPart, thisEnclosingJoinPointStaticPart);
        logger.info("BEFORE");
    }

    Object around() : loggingInfo() {
        Object retValue = proceed();
        helper.withReturnValue(objectToString(retValue));
        return retValue;
    }

    after(): loggingInfo() {
        helper.withExecutionTime((int) (Math.random() * 1000));
        logger.info("AFTER");
        helper.removeFullContext();
    }

    private String objectToString(Object obj) {
        return obj == null ? "null" : obj.toString();
    }
}
