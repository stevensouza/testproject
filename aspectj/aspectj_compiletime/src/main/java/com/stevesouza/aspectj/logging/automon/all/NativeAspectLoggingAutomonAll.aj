package com.stevesouza.aspectj.logging.automon.all;


import com.stevesouza.aspectj.logging.automon.LogTracingHelper;
import org.aspectj.lang.JoinPoint;
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
    pointcut loggingInfo(): within(com.stevesouza.aspectj.logging.automon.all.MyLoggerClassAll);


    before(): loggingInfo() {
        helper.withKind(thisJoinPointStaticPart).
                withSignature(thisJoinPointStaticPart).
                withParameters(thisJoinPoint).
                withThis(thisJoinPoint);
        // return value too.
        // for call logging not tracing
        helper.withEnclosingSignature(thisEnclosingJoinPointStaticPart).
        withTarget(thisJoinPoint);
        logger.info("BEFORE");
        //        helper.withKind(thisJoinPointStaticPart). basic
//                withSignature(thisJoinPointStaticPart). basic
//                withParameters(thisJoinPoint). intermediate, all, execution, call? others?
//                withThis(thisJoinPoint); all, execution, call? others?
//        // for call logging not tracing
//        helper.withEnclosingSignature(thisEnclosingJoinPointStaticPart). basic
//        withTarget(thisJoinPoint); intermediate? all? executin? call? other?
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
