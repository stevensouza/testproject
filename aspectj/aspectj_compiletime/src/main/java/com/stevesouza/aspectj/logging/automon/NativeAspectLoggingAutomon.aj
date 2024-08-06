package com.stevesouza.aspectj.logging.automon;


import com.stevesouza.aspectj.logging.MyLoggerClass;
import com.stevesouza.aspectj.nativestyle.utils.MethodArgumentExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.NDC;

import java.util.Random;
import java.util.UUID;

/**
 Tests call, withincode (any pointcut directly in a method), cflow (any pointcuts directly in the method as well as any pointcuts in the
 code that any of the methods call too).
 */
public aspect NativeAspectLoggingAutomon {
    private static final Logger logger = LoggerFactory.getLogger(NativeAspectLoggingAutomon.class);

    // Note within does all pointcuts within the class including this and static and constructors etc.
    // this() would not do static methods but only instance ones.
    pointcut loggingInfo(): execution(* com.stevesouza.aspectj.logging.automon.MyLoggerClass.*(..));


    before(): loggingInfo() {
//        if (MDC.get("session")==null) {
//            MDC.put("session", UUID.randomUUID().toString());
//        }
        NDC.push(thisEnclosingJoinPointStaticPart.getSignature().toShortString());
//        MDC.put("loggingMessageJson", MethodArgumentExtractor.toJson(thisJoinPoint));
        MDC.put("parameters", MethodArgumentExtractor.toMap(thisJoinPoint).toString());

        logger.info("Aspect Before");
        // this will display info on what method called call(myMethod3())
//        System.out.println(" thisEnclosingJoinPointStaticPart: " + thisEnclosingJoinPointStaticPart);
//        System.out.println(" thisEnclosingJoinPointStaticPart.getSignature().toShortString(): " + thisEnclosingJoinPointStaticPart.getSignature().toShortString());
//        System.out.println(" loggingMessage="+message);
    }

    after(): loggingInfo() {
        MDC.put("executionTimeMs", String.valueOf((int) (Math.random() * 1000)));
        logger.info("Aspect After");
        NDC.pop();
        MDC.remove("parameters");
        MDC.remove("executionTimeMs");
    }
}
