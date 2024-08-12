package com.stevesouza.aspectj.logging.automon.basic;


import com.stevesouza.aspectj.logging.automon.LogTracingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 Tests call, withincode (any pointcut directly in a method), cflow (any pointcuts directly in the method as well as any pointcuts in the
 code that any of the methods call too).
 */
public aspect NativeAspectLoggingAutomonBasic {
    private static final Logger logger = LoggerFactory.getLogger(NativeAspectLoggingAutomonBasic.class);

    private final LogTracingHelper helper = LogTracingHelper.getInstance();

    // Note within does all pointcuts within the class including this and static and constructors etc.
    // this() would not do static methods but only instance ones.
//    pointcut loggingInfo(): execution(* com.stevesouza.aspectj.logging.automon.MyLoggerClass.*(..));
    pointcut loggingInfo(): within(com.stevesouza.aspectj.logging.automon.basic.MyLoggerClassBasic);


    before(): loggingInfo() {
        helper.
                withSignature(thisJoinPointStaticPart).
                withEnclosingSignature(thisEnclosingJoinPointStaticPart).// for execution this isn't needed. it is redundant.
                withKind(thisJoinPointStaticPart);
        logger.info("BEFORE");
    }

    after(): loggingInfo() {
        helper.withExecutionTime((int) (Math.random() * 1000));
        logger.info("AFTER");
        helper.removeExecutionTime()
                .removeKind()
                .removeSignature()
                .removeEnclosingSignature();
    }
}
